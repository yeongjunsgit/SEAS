package com.ssafy.seas.quiz.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

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

        private Boolean isCorrect;
        private Boolean isUsedHint;

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

        public void setIsCorrect(Boolean result){
            this.isCorrect = result;
        }

        public void setUsedHint(Boolean isUsed){
            this.isUsedHint = isUsed;
        }

        @Override
        public String toString(){
            return "[" + this.quiz + " | " + this.hint + "]";
        }
    }

    @RequiredArgsConstructor
    @Getter
    @Setter
    public static class QuizWeightInfoDto{
        private final Integer quizId;
        private final Double quizInterval;
        private final Double ef;

    }



}
