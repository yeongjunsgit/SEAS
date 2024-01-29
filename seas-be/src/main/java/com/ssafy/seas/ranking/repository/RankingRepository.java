package com.ssafy.seas.ranking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.seas.ranking.entity.Ranker;

// @Repository
public interface RankingRepository extends
	JpaRepository<Ranker, Integer>, RankerRepositoryCustom {
	// RankerRepositoryCustom 인터페이스를 상속받아야 한다.
	// 그러므로 이를 구현한 구현체인 impl이 필요하다.
}
