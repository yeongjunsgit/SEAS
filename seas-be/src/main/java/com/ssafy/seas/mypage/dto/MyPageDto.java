package com.ssafy.seas.mypage.dto;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.seas.quiz.util.QuizUtil;

import lombok.Builder;
import lombok.Getter;

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

	@Getter
	public static class PerformanceGraph {
		Integer categoryId;
		String categoryName;
		List<ScoreHistory> history = new ArrayList<>();

		@Builder
		public PerformanceGraph(Integer categoryId, String categoryName) {
			this.categoryId = categoryId;
			this.categoryName = categoryName;
		}
	}

	@Getter
	public static class ScoreHistory {

		private LocalDate date;
		private Double averageScore;
		private Integer scoreCount;

		public ScoreHistory(Date date, Double averageScore, Long scoreCount) {
			this.date = date.toLocalDate();
			this.averageScore = QuizUtil.toFixed(averageScore, 1);
			this.scoreCount = Math.toIntExact(scoreCount);
		}

		@Builder
		public ScoreHistory(LocalDate date, Double averageScore, Integer scoreCount) {
			this.date = date;
			this.averageScore = averageScore;
			this.scoreCount = scoreCount;
		}

	}

	@Getter
	public static class ScoreHistoryDetail extends ScoreHistory {
		private Integer categoryId;
		public ScoreHistoryDetail(Integer categoryId, Date date, Double averageScore, Long scoreCount) {
			super(date, averageScore, scoreCount);
			this.categoryId = categoryId;
		}
	}

}
