package com.ssafy.seas.ranking.repository;

import java.util.List;

import com.ssafy.seas.ranking.dto.BadgeDto;
import com.ssafy.seas.ranking.dto.RankerDto;

public interface RankerRepositoryCustom {
	List<RankerDto.RankResponse> getRankers();
	List<BadgeDto.BadgeResponse> getBadgeList(String nickname);
	List<RankerDto.RankResponse> getMyRank(String uuid);
	List<RankerDto.RankResponseWithRanking> getRankByNickname(String nickname);
}
