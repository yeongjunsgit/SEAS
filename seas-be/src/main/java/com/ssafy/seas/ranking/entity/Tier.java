package com.ssafy.seas.ranking.entity;

import com.ssafy.seas.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tier")
public class Tier extends BaseEntity {
	@Column(nullable = false, unique = true)
	private String name;
	@Column(name = "min_score", nullable = false, unique = true)
	private Integer minScore;
	@Column(name = "max_score", nullable = false, unique = true)
	private Integer maxScore;

	@Builder	// AllArgs.. 쓰면 사용자가 값 넣을 수 있으니까 못하게 Builder 사용
	public Tier(String name, Integer minScore, Integer maxScore) {
		this.name = name;
		this.minScore = minScore;
		this.maxScore = maxScore;
	}
}
