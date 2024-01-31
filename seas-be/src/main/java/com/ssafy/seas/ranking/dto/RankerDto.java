package com.ssafy.seas.ranking.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RankerDto {
	@Getter
	@Builder
	@NoArgsConstructor
	public static class Response {
		private String memberId;
		private String nickname;
		private String tier;

		@QueryProjection	// 이게 있어야 select, .. 를 쓸 수 있다.
		public Response(String memberId, String nickname, String tier) {
			this.memberId = memberId;
			this.nickname = nickname;
			this.tier = tier;
		}
	}
}
