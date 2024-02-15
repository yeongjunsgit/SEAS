package com.ssafy.seas.quiz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

import com.ssafy.seas.category.dto.CategoryDto;
import com.ssafy.seas.category.util.CategoryUtil;
import com.ssafy.seas.common.constants.ErrorCode;
import com.ssafy.seas.common.exception.CustomException;
import com.ssafy.seas.member.dto.MemberDto;
import com.ssafy.seas.member.repository.MemberRepository;
import com.ssafy.seas.member.util.MemberUtil;
import com.ssafy.seas.quiz.dto.QuizAnswerDto;
import com.ssafy.seas.quiz.dto.QuizDto;
import com.ssafy.seas.quiz.dto.QuizHintDto;
import com.ssafy.seas.quiz.dto.QuizListDto;
import com.ssafy.seas.quiz.dto.QuizResultDto;
import com.ssafy.seas.quiz.dto.QuizTierDto;
import com.ssafy.seas.quiz.repository.CorrectAnswerRepository;
import com.ssafy.seas.quiz.repository.FactorRepository;
import com.ssafy.seas.quiz.repository.IncorrectNoteRepository;
import com.ssafy.seas.quiz.repository.QuizCustomRepository;
import com.ssafy.seas.quiz.repository.WrongAnswerRepostory;
import com.ssafy.seas.quiz.util.QuizUtil;
import com.ssafy.seas.ranking.entity.Badge;
import com.ssafy.seas.ranking.entity.MemberBadge;
import com.ssafy.seas.ranking.repository.BadgeRepository;
import com.ssafy.seas.ranking.repository.MemberBadgeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class QuizService {

	private final QuizCustomRepository quizCustomRepository;
	private final FactorRepository factorRepository;
	private final WrongAnswerRepostory wrongAnswerRepostory;
	private final CorrectAnswerRepository correctAnswerRepository;
	private final IncorrectNoteRepository incorrectNoteRepository;
	private final BadgeRepository badgeRepository;
	private final MemberBadgeRepository memberBadgeRepository;
	private final QuizUtil quizUtil;
	private final MemberUtil memberUtil;
	private final CategoryUtil categoryUtil;
	private final MemberRepository memberRepository;

	private final RedisTemplate<Integer, Map<Integer, QuizDto.QuizFactorDto>> redisTemplate;

    /*
        현재 문제 :
            swagger에서  가 들어갈 때 JSON 에러 남
     */

	public QuizListDto.Response getQuizzes(Integer categoryId) {

		Integer memberId = memberUtil.getLoginMemberId();

		List<QuizListDto.QuizInfo> quizInfoList = new ArrayList<>();

		// factor 테이블에 아직 없을 시에는 interval, ef null
		List<QuizDto.QuizFactorDto> quizFactors = quizCustomRepository.findAllQuizInnerJoin(memberId, categoryId);
		List<QuizDto.QuizWeightInfoDto> quizWeightInfos = new ArrayList<>();

		// factor 테이블의 정보를 저장
		quizWeightInfos.addAll(quizFactors.stream().map(dto -> {
			return new QuizDto.QuizWeightInfoDto(dto.getQuizId(), dto.getQuizInterval(), dto.getEf());
		}).collect(Collectors.toList()));

		for (int i = 0; i < 10; i++) {
			double[][] prefixWeightList = quizUtil.getPrefixWeightArray(quizWeightInfos);
			double[] selectedQuizInfo = quizUtil.selectQuizzes(prefixWeightList);
			int foundIndex = (int)selectedQuizInfo[2];
			quizWeightInfos.remove(foundIndex);

			int quizId = (int)selectedQuizInfo[0];
			String quiz = quizFactors.stream().filter(dto -> dto.getQuizId() == quizId).findFirst().get().getQuiz();
			quizInfoList.add(new QuizListDto.QuizInfo(quizId, quiz));
		}

		quizUtil.storeQuizToRedis(memberId, quizFactors, quizInfoList);

		return new QuizListDto.Response(quizInfoList);
	}

	public QuizHintDto.Response getHint(Integer quizId) {

		Integer memberId = memberUtil.getLoginMemberId();

		quizUtil.updateHintState(memberId, quizId);
		String hint = quizUtil.getQuizHint(memberId, quizId);
		return new QuizHintDto.Response(quizId, hint);
	}

	@Transactional
	public QuizAnswerDto.Response getSubmitResult(QuizAnswerDto.Request request, Integer categoryId,
		Integer quizId) throws ServerErrorException {

		String submit = request.getSubmit()
			.replaceAll("\s+", "")
			.replaceAll("\t+", "")
			.replaceAll(" ", "")
			.toLowerCase()
			.trim();

		List<String> quizAnswers = quizCustomRepository.findAllQuizAnswerByQuizId(quizId);

		Integer memberId = memberUtil.getLoginMemberId();
		log.info("현재 로그인한 유저의 ID : {}", memberId);

		for (String quizAnswer : quizAnswers) {
			if (quizAnswer.equals(submit)) {
				// 퀴즈 정답 횟수 + 1
				// 카테고리 별 맞힌 횟수 + 1

				quizUtil.updateQuizAnswerState(memberId, quizId);
				QuizAnswerDto.UpdatedFactors factor = quizUtil.getNewFactor(memberId, quizId, categoryId);

				// 포인트, 점수 저장
				quizUtil.updateQuizPointAndScore(memberId, quizId, factor.getPoint(), factor.getScore());
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

	@org.springframework.transaction.annotation.Transactional
	public void applyTotalResult(Integer memberId) {
		Map<Integer, QuizDto.QuizFactorDto> value = redisTemplate.opsForValue().get(memberId);
		Integer categoryId = 0;
		Integer totalScore = 0;

		for (Map.Entry<Integer, QuizDto.QuizFactorDto> quizResult : value.entrySet()) {
			QuizDto.QuizFactorDto factorDto = quizResult.getValue();
			Integer quizId = factorDto.getQuizId();
			categoryId = factorDto.getCategoryId();

			log.info("현재 interval : {} ef : {}", factorDto.getEf(), factorDto.getQuizInterval());
			totalScore += factorDto.getScore();
			// 맞았을 때
			if (factorDto.getIsCorrect()) {
				correctAnswerRepository.saveOrUpdateStreakAndSolvedQuiz(factorDto, memberId);
				correctAnswerRepository.saveOrUpdateFactor(factorDto, memberId);
				incorrectNoteRepository.deleteByMemberIdAndQuizId(memberId, factorDto.getQuizId());
			} else {
				wrongAnswerRepostory.saveOrUpdateIncorrectNoteAndSolvedQuiz(memberId, quizId);
				wrongAnswerRepostory.saveOrUpdateFactor(memberId, factorDto);
			}
		}

		quizCustomRepository.saveScoreHistory(memberId, categoryId, totalScore);

		applyBadge(memberId, categoryId);
	}

	@org.springframework.transaction.annotation.Transactional
	public boolean applyBadge(Integer memberId, Integer categoryId) {
		List<CategoryDto.Response> categories = categoryUtil.getCategories();

		// categoryId에 해당하는 category 가져오기
		CategoryDto.Response category = categories.stream()
			.filter(c -> categoryId.equals(c.getId()))
			.findFirst()
			.orElseThrow(() -> new CustomException(ErrorCode.BAD_CATEGORY_ID.getMessage()));

		// 카테고리에 해당하는 뱃지를 이미 갖고 있는지 검사
		Badge badge = badgeRepository.findByName(category.getName())
			.orElseThrow(() -> new CustomException(ErrorCode.BAD_BADGE_NAME.getMessage()));

		Optional<MemberBadge> memberBadge = memberBadgeRepository.findByMemberIdAndBadgeId(memberId, badge.getId());

		// 이미 갖고 있으면 더이상 진행 안 함
		if (memberBadge.isPresent()) {
			return false;
		}

		// 해당 카테고리 맞힌 개수: solved_quiz의 quiz_id와 quiz의 id 및 quiz의 category_id와 category의 id 조인한 테이블에서 category_id가 categoryId이면서 member_id가 memberId 이면서 correctCount가 1이상인 데이터 개수
		Long correctCountPerCategory = correctAnswerRepository.getCorrectCountPerCategoryByMemberIdAndCategoryId(
			memberId, categoryId);

		// 해당 카테고리 맞힌 개수가 category 문제 수와 일치하는지 검사
		if (Objects.equals(correctCountPerCategory.intValue(), category.getQuizCount())) {
			// 일치하면 새로 엔티티 생성 및 save
			MemberBadge newMemberBadge = new MemberBadge(memberId, badge.getId());
			memberBadgeRepository.save(newMemberBadge);
		}

		return true;
	}

	public QuizResultDto.Response getTotalResult() {

		Integer memberId = memberUtil.getLoginMemberId();

		applyTotalResult(memberId);

		QuizResultDto.Response response = quizUtil.getResult(memberId);
		quizUtil.resetRedis(memberId);
		return response;
	}

	public QuizDto.BaseResponse redisReset() {
		Integer memberId = memberUtil.getLoginMemberId();
		quizUtil.resetRedis(memberId);

		return new QuizDto.BaseResponse("레디스 값 삭제 성공");
	}

	public QuizTierDto.Response getCurrentTier() {

		Integer memberId = memberUtil.getLoginMemberId();

		MemberDto.MyInfoResponse myInfo = memberRepository.getMyInfoResponse(memberId);

		return new QuizTierDto.Response(myInfo.getTier(), false);
	}

	public QuizTierDto.Response getTotalTier(String prevTier) {

		Integer memberId = memberUtil.getLoginMemberId();

		MemberDto.MyInfoResponse myInfo = memberRepository.getMyInfoResponse(memberId);

		log.info("POINT : {}", myInfo.getPoint());
		boolean isUpgraded = !myInfo.getTier().equals(prevTier);

		return new QuizTierDto.Response(myInfo.getTier(), isUpgraded);
	}
}
