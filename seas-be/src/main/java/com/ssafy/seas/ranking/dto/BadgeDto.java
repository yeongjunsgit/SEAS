package com.ssafy.seas.ranking.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BadgeDto {

	@Builder
	@Getter
	@NoArgsConstructor
	public static class BadgeResponse {
		private Integer id;
		private String name;

		@QueryProjection
		public BadgeResponse(Integer id, String name){
			this.id = id;
			this.name = name;
		}
	}
}
