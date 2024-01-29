package com.ssafy.seas.ranking.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssafy.seas.ranking.dto.RankerDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class RankerRepository {
	@PersistenceContext
	private EntityManager entityManager;

	public List<RankerDto> getRankers() {
		String jpql = "SELECT new com.ssafy.seas.ranking.dto.RankerDto(m.memberId, m.nickname, t.name tier) " +
					  "FROM Member m JOIN Tier t ON m.point BETWEEN t.minScore AND t.maxScore";

		return entityManager.createQuery(jpql, RankerDto.class).getResultList();
	}
}
