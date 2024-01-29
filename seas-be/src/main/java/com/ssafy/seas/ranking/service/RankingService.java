package com.ssafy.seas.ranking.service;

import org.springframework.stereotype.Service;

import com.ssafy.seas.ranking.repository.RankerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RankingService {
	private final RankerRepository rankerRepository;

	// public List<RankerDto> getRankers(){
	// 	return rankerRepository.getRankers();
	// }

}
