package com.ssafy.seas.quiz.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Quality {
	// SM2 등급
	QUIZ_CORRECT(3),
	QUIZ_CORRECT_WITH_HINT(2),
	FLASHCARD_VIEW(1),
	QUIZ_WRONG(0);

	private final int value;
}
