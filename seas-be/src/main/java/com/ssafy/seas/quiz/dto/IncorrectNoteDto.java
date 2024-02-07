package com.ssafy.seas.quiz.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class IncorrectNoteDto {
	@Getter
	@AllArgsConstructor
	public static class QuizIdPerCategory {
		private Integer categoryId;
		private String categoryName;
		private List<Integer> quizIds = new ArrayList<>();
	}
}
