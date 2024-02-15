package com.ssafy.seas.quiz.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class QuizTierDto {

    @Getter
    @RequiredArgsConstructor
    public static class Request{
        private String prevTier;
    }

    @Getter
    public static class Response{
        private boolean isUpgraded;
        private String tier;

        public Response(){}
        public Response(String tier, boolean isUpgraded){
            this.tier = tier;
            this.isUpgraded = isUpgraded;
        }
    }
}
