package com.ssafy.seas.ranking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.seas.ranking.dto.RankerDto;

@Repository
public interface RankingRepository extends
	JpaRepository<RankerDto.Response, Integer>, RankerRepositoryCustom {
	// RankerRepositoryCustom 인터페이스를 상속받아야 한다.
	// 그러므로 이를 구현한 구현체인 impl이 필요하다.
}
