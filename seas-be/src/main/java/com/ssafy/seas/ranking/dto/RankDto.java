package com.ssafy.seas.ranking.dto;

import java.util.List;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RankDto {
	@Getter
	@Builder
	@NoArgsConstructor
	public static class Response {
		List<RankerDto.RankResponse> rankersTop3;
		List<RankerDto.RankResponse> rankers;
		RankerDto.RankResponse myInfo;

		@QueryProjection
		public Response(List<RankerDto.RankResponse> rankersTop3, List<RankerDto.RankResponse> rankers, RankerDto.RankResponse myInfo){
			this.rankersTop3 = rankersTop3;
			this.rankers = rankers;
			this.myInfo = myInfo;
		}
	}
}
