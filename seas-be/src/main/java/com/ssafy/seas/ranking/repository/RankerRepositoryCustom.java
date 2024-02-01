package com.ssafy.seas.ranking.repository;

import java.util.List;

import com.ssafy.seas.ranking.dto.RankerDto;

public interface RankerRepositoryCustom {
	List<RankerDto.RankResponse> getRankers();
	List<RankerDto.RankResponse> getMyRank(String uuid);
}
