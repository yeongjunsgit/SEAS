package com.ssafy.seas.quiz.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Interval {

	FIRST(1),
	SECOND(2),
	MAXIMUM(365),
	DEFAULT(Interval.FIRST.getValue());

	private final int value;
}
