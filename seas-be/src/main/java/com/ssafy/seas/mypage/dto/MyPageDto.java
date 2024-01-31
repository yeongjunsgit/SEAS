package com.ssafy.seas.mypage.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MyPageDto {

	@Getter
	public static class QuizRate {
		private String category;
		private Double rate;

		@Builder
		@QueryProjection
		public QuizRate(String category, Double rate) {
			this.category = category;
			this.rate = rate;
		}
	}

	public static class CorrectCount {
		private Integer categoryId;
		private String categoryName;
		private Integer correctCount;

		@QueryProjection
		public CorrectCount(Integer categoryId, String categoryName, Integer correctCount) {
			this.categoryId = categoryId;
			this.categoryName = categoryName;
			this.correctCount = correctCount;
		}
	}
}
