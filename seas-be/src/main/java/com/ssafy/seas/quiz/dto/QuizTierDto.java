package com.ssafy.seas.quiz.dto;

import lombok.Getter;

public class QuizTierDto {


    @Getter
    public static class Response{
        private boolean isUpgraded;
        private String tier;

        public Response(){}
        public Response(boolean isUpgraded, String tier){
            this.isUpgraded = isUpgraded;
            this.tier = tier;
        }

    }


}
