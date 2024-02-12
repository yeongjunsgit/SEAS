package com.ssafy.seas.quiz.constant;

import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EasinessFactor {
	MINIMUM(1.3), // 잘 모름
	MAXIMUM(2.5), // 잘 알고 있음
	DEFAULT(EasinessFactor.MINIMUM.getValue())
	;

	private final double value;
}

