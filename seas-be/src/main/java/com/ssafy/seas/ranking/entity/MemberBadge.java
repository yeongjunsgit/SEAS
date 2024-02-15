package com.ssafy.seas.ranking.entity;

import com.ssafy.seas.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberBadge extends BaseEntity {
	@Column
	private Integer memberId;
	@Column
	private Integer badgeId;
}
