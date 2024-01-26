package com.ssafy.seas.ranking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.seas.ranking.dto.RankerDto;
import com.ssafy.seas.ranking.repository.RankerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RankingService {
	private final RankerRepository rankerRepository;

	public List<RankerDto> getRankers(){
		return rankerRepository.getRankers();
	}

}
