package com.ssafy.seas.quiz.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

public class QuizAnswerDto {


    @Getter
    @Setter
    public static class Request{
        private final String submit;

        public Request(String submit){
            this.submit = submit;
        }
    }

    @Getter
    @Setter
    public static class Response{
        private Boolean result = false;

        public Response(){}
        public Response(Boolean result){
            this.result = result;
        }
    }

    @Getter
    public static class UpdatedFactors{
        private final Integer quizId;
        private final Integer memberId;
        private final Double interval;
        private final Double ef;
        private Integer score;
        private Integer point;

        @QueryProjection
        public UpdatedFactors(Integer quizId, Integer memberId, Double interval, Double ef){
            this.quizId = quizId;
            this.memberId = memberId;
            this.interval = interval;
            this.ef = ef;
        }

        public UpdatedFactors(Integer quizId, Integer memberId, Double interval, Double ef, Integer score, Integer point){
            this.quizId = quizId;
            this.memberId = memberId;
            this.interval = interval;
            this.ef = ef;
            this.score = score;
            this.point = point;
        }
    }

    @Getter
    @Setter
    public static class solvedQuizWithIncorrectNote{
        private final Integer quizId;
        private final Integer memberId;
        private final Integer failCount;
        private final Integer correcCount;

        @QueryProjection
        public solvedQuizWithIncorrectNote(Integer quizId, Integer memberId, Integer failCount, Integer correcCount){
            this.quizId = quizId;
            this.memberId = memberId;
            this.failCount = failCount;
            this.correcCount = correcCount;
        }

    }

}
