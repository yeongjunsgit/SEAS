package com.ssafy.seas.quiz.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
public class QuizDto {

    @Getter
    @RequiredArgsConstructor
    public static class QuizFactorDto implements Serializable{
        private Double quizInterval;
        private Double ef;

        private Integer cardQuizId;
        private Integer memberId;

        private Integer quizId;
        private String quiz;
        private String hint;


        @QueryProjection
        public QuizFactorDto(Integer memberId, Integer cardQuizId, Integer quizId, String quiz, String hint, Double quizInterval, Double ef){
            this.memberId = memberId;
            this.cardQuizId = cardQuizId;
            this.quizId = quizId;
            this.quiz = quiz;
            this.hint = hint;
            this.quizInterval = quizInterval;
            this.ef = ef;
        }
    }

    @RequiredArgsConstructor
    @Getter
    @Setter
    public static class QuizWeightInfo{
        private Integer quizId;
        private Double quizInterval;
        private Double ef;

        public QuizWeightInfo(Integer quizId, Double quizInterval, Double ef) {
            this.quizId = quizId;
            this.quizInterval = quizInterval;
            this.ef = ef;
        }
    }

}
