package com.ssafy.seas.quiz.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

public class QuizListDto {

    @RequiredArgsConstructor
    @Getter
    @Setter
    public static class Request{
        Integer memberId;
        Integer categoryId;
    }

    @RequiredArgsConstructor
    @Getter
    public static class Response{
        List<QuizInfo> quizList;

        public Response(List<QuizInfo> quizList){
            this.quizList = quizList;
        }
    }

    @Getter
    public static class QuizInfo{
        Integer quizId;
        String quiz;

        @QueryProjection
        public QuizInfo(Integer quizId, String quiz){
            this.quizId = quizId;
            this.quiz = quiz;
        }
    }

}
