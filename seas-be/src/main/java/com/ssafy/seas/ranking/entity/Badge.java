package com.ssafy.seas.ranking.entity;

import com.ssafy.seas.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Badge extends BaseEntity {
	@Column(unique = true)
	private String name;
}
