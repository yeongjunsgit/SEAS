package com.ssafy.seas.quiz.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Interval {

	FIRST(1.0),
	SECOND(6.0),
	MAXIMUM(365.0),
	DEFAULT(Interval.FIRST.getValue());

	private final double value;
}
