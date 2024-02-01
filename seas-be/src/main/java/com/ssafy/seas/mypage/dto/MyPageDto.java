package com.ssafy.seas.mypage.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class MyPageDto {

	@Getter
	public static class QuizRate {
		private Integer categoryId;
		private String categoryName;
		private Double rate;

		@Builder
		@QueryProjection
		public QuizRate(Integer categoryId, String categoryName, Double rate) {
			this.categoryId = categoryId;
			this.categoryName = categoryName;
			this.rate = rate;
		}
	}

	@Getter
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

	public static class PerformanceGraph {

	}
}
