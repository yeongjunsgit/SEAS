package com.ssafy.seas.mypage.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;

public class StreakDto {

	@Getter
	public static class Response {
		private Integer grade;
		private LocalDateTime createdAt;

		@Builder
		public Response(LocalDateTime createdAt, Integer grade) {
			this.createdAt = createdAt;
			this.grade = grade;
		}
	}
}
