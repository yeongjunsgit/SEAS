package com.ssafy.seas.member.entity;

import com.ssafy.seas.common.entity.BaseEntity;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tier extends BaseEntity {
	private String name;
	private Integer minScore = 0;
	private Integer maxScore = 0;
}
