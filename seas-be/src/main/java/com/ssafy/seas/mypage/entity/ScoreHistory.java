package com.ssafy.seas.mypage.entity;

import com.ssafy.seas.category.entity.Category;
import com.ssafy.seas.common.entity.BaseEntity;
import com.ssafy.seas.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "score_history")
public class ScoreHistory extends BaseEntity {

	@Column(name = "score", nullable = false, columnDefinition = "int default 0")
	private Integer score;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;
}
