package com.ssafy.seas.mypage.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

public class StreakDto {

	@Getter
	public static class Response {
		private Integer grade;
		private LocalDateTime createdAt;

		public Response(Timestamp createdAt, Integer grade) {
			this.createdAt = createdAt.toLocalDateTime();
			this.grade = grade;
		}
	}
}
