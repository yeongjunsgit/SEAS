package com.ssafy.seas.ranking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.seas.ranking.dto.RankerDto;
import com.ssafy.seas.ranking.repository.RankerRepositoryImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QueryDslRankerService {
	private final RankerRepositoryImpl queryDslRankerRepository;

	public List<RankerDto.Response> getRankers() {
		return queryDslRankerRepository.getRankers();
	}
}
