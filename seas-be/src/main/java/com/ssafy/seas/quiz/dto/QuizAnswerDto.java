package com.ssafy.seas.quiz.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class QuizAnswerDto {


    @Getter
    @Setter
    public static class Request{
        private String submit;

        public Request(){}
        public Request(String submit){
            this.submit = submit;
        }

        public Request(Integer submit){
            this.submit = submit.toString();
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
    @ToString
    public static class UpdatedFactors{
        private final Integer memberId;
        private final Integer quizId;
        private final Integer categoryId;
        private final Double interval;
        private final Double ef;
        private Integer score;
        private Integer point;


        public UpdatedFactors(Integer memberId, Integer quizId, Integer categoryId, Double interval, Double ef, Integer score, Integer point){
            this.memberId = memberId;
            this.quizId = quizId;
            this.categoryId = categoryId;
            this.interval = interval;
            this.ef = ef;
            this.score = score;
            this.point = point;
        }

        @Override
        public String toString(){
            return "[ MEMBER ID " + this.memberId + "| QUIZID " + this.quizId + " | CATEGORY " + this.categoryId + "| INTERVAL " + this.interval + "| EF " + this.ef + "| SCORE " + this.score + "| POINT " +this.point + "]";
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
