package com.ssafy.seas.quiz.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class QuizWeightFactorDto {
    private final Double interval;
    private final Double ef;
}
