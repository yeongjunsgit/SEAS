package com.ssafy.seas.ranking.repository;

// @Repository
// public interface RankingRepository extends JpaRepository<Ranker, Integer>, RankerRepositoryCustom {
public interface RankingRepository extends RankerRepositoryCustom {
	// RankerRepositoryCustom 인터페이스를 상속받아야 한다.
	// 그러므로 이를 구현한 구현체인 impl이 필요하다.
}
