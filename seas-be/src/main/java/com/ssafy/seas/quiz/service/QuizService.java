package com.ssafy.seas.quiz.service;


import com.ssafy.seas.quiz.dto.QuizDto;
import com.ssafy.seas.quiz.dto.QuizListDto;
import com.ssafy.seas.quiz.repository.QuizCustomRepository;
import com.ssafy.seas.quiz.util.QuizUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final QuizCustomRepository quizCustomRepository;
    private final QuizUtil quizUtil;

    public QuizService(QuizCustomRepository quizCustomRepository, QuizUtil quizUtil) {
        this.quizCustomRepository = quizCustomRepository;
        this.quizUtil = quizUtil;
    }

    public QuizListDto.Response getQuizzes(QuizListDto.Request request){

        Integer memberId = request.getMemberId();
        Integer categoryId = request.getCategoryId();

        List<QuizListDto.QuizInfo> quizInfoList = new ArrayList<>();

        List<QuizDto.QuizFactorDto> quizFactors = quizCustomRepository.findAllQuizInnerJoin(memberId, categoryId);
        List<QuizDto.QuizWeightInfo> quizWeightInfos =
                quizFactors.stream().map(dto -> {
            return new QuizDto.QuizWeightInfo(dto.getQuizId(), dto.getQuizInterval(), dto.getEf());
        }).collect(Collectors.toList());

        for(int i = 0; i < 10; i++) {
            double[][] prefixWeightList = quizUtil.getPrefixWeightMap(quizWeightInfos);
            double[] selectedQuizInfo = quizUtil.selectQuizzes(prefixWeightList);
            int foundIndex = (int) selectedQuizInfo[2];
//            quizWeightInfos.get(foundIndex).setQuizInterval(0.0);
//            quizWeightInfos.get(foundIndex).setEf(0.0);

            quizWeightInfos.remove(foundIndex);

            int quizId = (int) selectedQuizInfo[0];
            String quiz = quizFactors.stream().filter(dto -> dto.getQuizId() == quizId).findFirst().get().getQuiz();
            quizInfoList.add(new QuizListDto.QuizInfo(quizId, quiz));
        }

        return new QuizListDto.Response(quizInfoList);
    }

}
