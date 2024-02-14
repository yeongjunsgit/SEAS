package com.ssafy.seas.ranking.dto;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberBadgeDto {

	@Builder
	@Getter
	@NoArgsConstructor
	public static class MemberBadgeResponse {
		private Integer memberId;
		private Integer badgeId;

		@QueryProjection
		public MemberBadgeResponse(Integer memberId, Integer badgeId){
			this.memberId = memberId;
			this.badgeId = badgeId;
		}
	}
}
