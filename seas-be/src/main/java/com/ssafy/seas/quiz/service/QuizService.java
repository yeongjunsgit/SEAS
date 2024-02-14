package com.ssafy.seas.quiz.service;


import com.ssafy.seas.member.dto.MemberDto;
import com.ssafy.seas.member.repository.MemberRepository;
import com.ssafy.seas.member.util.MemberUtil;
import com.ssafy.seas.quiz.dto.*;
import com.ssafy.seas.quiz.repository.*;
import com.ssafy.seas.quiz.util.QuizUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuizService {

    private final QuizCustomRepository quizCustomRepository;
    private final FactorRepository factorRepository;
    private final WrongAnswerRepostory wrongAnswerRepostory;
    private final CorrectAnswerRepository correctAnswerRepository;
    private final QuizUtil quizUtil;
    private final MemberUtil memberUtil;
    private final MemberRepository memberRepository;

    private final RedisTemplate<Integer, Map<Integer, QuizDto.QuizFactorDto>> redisTemplate;

    /*
        현재 문제 :
            swagger에서  가 들어갈 때 JSON 에러 남
     */


    public QuizListDto.Response getQuizzes(Integer categoryId){

        Integer memberId = memberUtil.getLoginMemberId();

        List<QuizListDto.QuizInfo> quizInfoList = new ArrayList<>();

        // factor 테이블에 아직 없을 시에는 interval, ef null
        List<QuizDto.QuizFactorDto> quizFactors = quizCustomRepository.findAllQuizInnerJoin(memberId, categoryId);
        List<QuizDto.QuizWeightInfoDto> quizWeightInfos = new ArrayList<>();

        // factor 테이블의 정보를 저장
        quizWeightInfos.addAll(
                quizFactors.stream().map(dto -> {
                    return new QuizDto.QuizWeightInfoDto(dto.getQuizId(), dto.getQuizInterval(), dto.getEf());
                }).collect(Collectors.toList())
        );

        for(int i = 0; i < 10; i++) {
            double[][] prefixWeightList = quizUtil.getPrefixWeightArray(quizWeightInfos);
            double[] selectedQuizInfo = quizUtil.selectQuizzes(prefixWeightList);
            int foundIndex = (int) selectedQuizInfo[2];
            quizWeightInfos.remove(foundIndex);

            int quizId = (int) selectedQuizInfo[0];
            String quiz = quizFactors.stream().filter(dto -> dto.getQuizId() == quizId).findFirst().get().getQuiz();
            quizInfoList.add(new QuizListDto.QuizInfo(quizId, quiz));
        }

        quizUtil.storeQuizToRedis(memberId, quizFactors, quizInfoList);

        return new QuizListDto.Response(quizInfoList);
    }

    public QuizHintDto.Response getHint(Integer quizId){

        Integer memberId = memberUtil.getLoginMemberId();

        quizUtil.updateHintState(memberId, quizId);
        String hint = quizUtil.getQuizHint(memberId, quizId);
        return new QuizHintDto.Response(quizId, hint);
    }


    @Transactional
    public QuizAnswerDto.Response getSubmitResult(QuizAnswerDto.Request request, Integer categoryId, Integer quizId) throws ServerErrorException {

        String submit = request.getSubmit().replaceAll("\s+", "").replaceAll("\t+", "").replaceAll(" ", "").toLowerCase().trim();

        List<String> quizAnswers = quizCustomRepository.findAllQuizAnswerByQuizId(quizId);

        Integer memberId = memberUtil.getLoginMemberId();
        log.info("현재 로그인한 유저의 ID : {}", memberId);

        for(String quizAnswer : quizAnswers){
            if(quizAnswer.equals(submit)){
                // 퀴즈 정답 횟수 + 1
                // 카테고리 별 맞힌 횟수 + 1

                quizUtil.updateQuizAnswerState(memberId, quizId);
                QuizAnswerDto.UpdatedFactors factor = quizUtil.getNewFactor(memberId, quizId, categoryId);
                
                // 포인트, 점수 저장
                quizUtil.updateQuizPointAndPoint(memberId, quizId, factor.getPoint(), factor.getScore());
                // 가중치 레디스 저장
                quizUtil.updateWeightFactor(memberId, quizId, factor.getEf(), factor.getInterval());
                
                //correctAnswerRepository.saveOrUpdateStreakAndScoreHistory(factor);
                //correctAnswerRepository.saveOrUpdateFactor(factor, memberId);
                return new QuizAnswerDto.Response(true);
            }
        }

        QuizAnswerDto.UpdatedFactors factor = quizUtil.getNewFactor(memberId, quizId, categoryId);
//        wrongAnswerRepostory.saveOrUpdateIncorrectNoteAndSolvedQuiz(memberId, quizId);
//        wrongAnswerRepostory.saveOrUpdateFactor(memberId, factor);
        return new QuizAnswerDto.Response(false);
    }


    public void applyTotalResult(Integer memberId){
        Map<Integer, QuizDto.QuizFactorDto> value = redisTemplate.opsForValue().get(memberId);
        Integer categoryId = 0;
        Integer totalScore = 0;

        for(Map.Entry<Integer, QuizDto.QuizFactorDto> quizResult : value.entrySet()){
            QuizDto.QuizFactorDto factorDto = quizResult.getValue();
            Integer quizId = factorDto.getQuizId();
            categoryId = factorDto.getCategoryId();

            log.info("현재 interval : {} ef : {}", factorDto.getEf(), factorDto.getQuizInterval());
            totalScore += factorDto.getScore();
            // 맞았을 때
            if(factorDto.getIsCorrect()){
                correctAnswerRepository.saveOrUpdateStreakAndSolvedQuiz(factorDto, memberId);
                correctAnswerRepository.saveOrUpdateFactor(factorDto, memberId);
            }
            else{
                wrongAnswerRepostory.saveOrUpdateIncorrectNoteAndSolvedQuiz(memberId, quizId);
                wrongAnswerRepostory.saveOrUpdateFactor(memberId, factorDto);
            }
        }

        quizCustomRepository.saveScoreHistory(memberId, categoryId, totalScore);
    }


    public QuizResultDto.Response getTotalResult(){

        Integer memberId = memberUtil.getLoginMemberId();

        applyTotalResult(memberId);

        QuizResultDto.Response response = quizUtil.getResult(memberId);
        quizUtil.resetRedis(memberId);
        return response;
    }

    public QuizDto.BaseResponse redisReset(){
        Integer memberId = memberUtil.getLoginMemberId();
        quizUtil.resetRedis(memberId);

        return new QuizDto.BaseResponse("레디스 값 삭제 성공");
    }

    public QuizTierDto.Response getCurrentTier(){

        Integer memberId = memberUtil.getLoginMemberId();

        MemberDto.MyInfoResponse myInfo = memberRepository.getMyInfoResponse(memberId);

        return new QuizTierDto.Response(myInfo.getTier(), false);
    }

    public QuizTierDto.Response getTotalTier(String prevTier){

        Integer memberId = memberUtil.getLoginMemberId();

        MemberDto.MyInfoResponse myInfo = memberRepository.getMyInfoResponse(memberId);

        log.info("POINT : {}", myInfo.getPoint());
        boolean isUpgraded = !myInfo.getTier().equals(prevTier);

        return new QuizTierDto.Response(myInfo.getTier(),isUpgraded);
    }
}
