package com.ssafy.seas.ranking.entity;

import com.ssafy.seas.common.entity.BaseEntity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ranker extends BaseEntity {
	private String memberId;
	private String nickname;
	private Integer point;
	private String tier;
}
