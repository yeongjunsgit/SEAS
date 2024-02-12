package com.ssafy.seas.quiz.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public class QuizResultDto {

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class Response {
        private Integer answerCount = 0;
        private Integer wrongCount = 0;
        private Integer usedHintCount = 0;
        private Integer earnedScore = 0;

        public void setCorrectState(){
            this.answerCount += 1;
            this.earnedScore += 10;
        }

        public void setWrongState(){
            this.wrongCount += 1;
        }

        public void setHintState(){
            this.usedHintCount += 1;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "answerCount=" + answerCount +
                    ", wrongCount=" + wrongCount +
                    ", usedHintCount=" + usedHintCount +
                    ", earnedScore=" + earnedScore +
                    '}';
        }
    }

}
