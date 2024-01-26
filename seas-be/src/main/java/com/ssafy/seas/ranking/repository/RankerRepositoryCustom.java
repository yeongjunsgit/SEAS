package com.ssafy.seas.ranking.repository;

import java.util.List;

import com.ssafy.seas.ranking.dto.RankerDto;

public interface RankerRepositoryCustom {
	List<RankerDto.Response> getRankers();
}
