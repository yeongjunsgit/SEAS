package com.ssafy.seas.quiz.dto;

import lombok.*;

public class QuizHintDto {

    @RequiredArgsConstructor
    @Getter
    @Setter
    public static class Request{
        Integer memberId;
    }

    @RequiredArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Response{
        Integer quizId;
        String hint;

        public Response(Integer quizId, String hint){
            this.quizId = quizId;
            this.hint = hint;
        }
    }

}
