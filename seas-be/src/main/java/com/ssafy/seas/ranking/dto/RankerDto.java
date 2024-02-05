package com.ssafy.seas.ranking.dto;

import java.util.ArrayList;
import java.util.List;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class RankerDto {
	@Getter
	@Builder
	@NoArgsConstructor
	public static class Response {
		private String memberId;
		private String nickname;
		private Integer point;
		private String tier;

		@QueryProjection	// 이게 있어야 select, .. 를 쓸 수 있다.
		public Response(String memberId, String nickname, Integer point, String tier) {
			this.memberId = memberId;
			this.nickname = nickname;
			this.point = point;
			this.tier = tier;
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	public static class RankResponse {
		private String nickname;
		private Integer point;
		private String tier;
		@Setter
		private List<BadgeDto.BadgeResponse> badgeList = new ArrayList<>();

		public RankResponse(String nickname, Integer point, String tier, List<BadgeDto.BadgeResponse> badgeList) {
			this.nickname = nickname;
			this.point = point;
			this.tier = tier;
			this.badgeList = badgeList;
		}

		@QueryProjection
		public RankResponse(String nickname, Integer point, String tier) {
			this.nickname = nickname;
			this.point = point;
			this.tier = tier;
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	public static class RankResponseWithRanking {
		private String nickname;
		private Integer point;
		private String tier;
		private Integer ranking;

		@QueryProjection
		public RankResponseWithRanking(String nickname, Integer point, String tier, Integer ranking) {
			this.nickname = nickname;
			this.point = point;
			this.tier = tier;
			this.ranking = ranking;
		}
	}
}
