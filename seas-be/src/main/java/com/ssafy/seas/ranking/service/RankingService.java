package com.ssafy.seas.ranking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.seas.ranking.dto.BadgeDto;
import com.ssafy.seas.ranking.dto.RankerDto;
import com.ssafy.seas.ranking.repository.RankerRepositoryCustom;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RankingService {
	private final RankerRepositoryCustom rankerRepositoryCustom;
	// private final RankerRepositoryImpl rankerRepositoryImpl;

	public List<RankerDto.RankResponse> getRankers(){
		return rankerRepositoryCustom.getRankers();
		// return rankerRepositoryImpl.getRankers();
	}

	public List<BadgeDto.BadgeResponse> getBadgeList(String nickname){
		return rankerRepositoryCustom.getBadgeList(nickname);
	}

	public List<RankerDto.RankResponse> getMyRank(String uuid){
		return rankerRepositoryCustom.getMyRank(uuid);
		// return rankerRepositoryImpl.getMyRank(uuid);
	}

	public List<RankerDto.RankResponseWithRanking> getRankByNickname(String nickname){
		return rankerRepositoryCustom.getRankByNickname(nickname);
	}

}
