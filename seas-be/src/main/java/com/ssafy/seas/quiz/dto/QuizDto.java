package com.ssafy.seas.quiz.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

public class QuizDto {

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class QuizFactorDto implements Serializable{
        private Double quizInterval;
        private Double ef;

        private Integer cardQuizId;
        private Integer memberId;

        private Integer quizId;
        private String quiz;
        private String hint;

        private Boolean isCorrect = false;
        private Boolean isUsedHint = false;

        @QueryProjection
        public QuizFactorDto(Integer memberId, Integer cardQuizId, Integer quizId, String quiz, String hint, Double quizInterval, Double ef){
            this.memberId = memberId;
            this.cardQuizId = cardQuizId;
            this.quizId = quizId;
            this.quiz = quiz;
            this.hint = hint;
            this.quizInterval = quizInterval;
            this.ef = ef;
            this.isCorrect = false;
            this.isUsedHint = false;
        }

        public void setIsCorrect(Boolean result){
            this.isCorrect = result;
        }

        public void setUsedHint(Boolean isUsed){
            this.isUsedHint = isUsed;
        }

        @Override
        public String toString() {
            return "QuizFactorDto{" +
                    "quizInterval=" + quizInterval +
                    ", ef=" + ef +
                    ", cardQuizId=" + cardQuizId +
                    ", memberId=" + memberId +
                    ", quizId=" + quizId +
                    ", quiz='" + quiz + '\'' +
                    ", hint='" + hint + '\'' +
                    ", isCorrect=" + isCorrect +
                    ", isUsedHint=" + isUsedHint +
                    '}';
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