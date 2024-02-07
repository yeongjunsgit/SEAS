package com.ssafy.seas.quiz.entity;

import com.ssafy.seas.common.entity.BaseEntity;
import com.ssafy.seas.member.entity.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IncorrectNote extends BaseEntity {
	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(targetEntity = Member.class)
	@JoinColumn(name = "quiz_id")
	private Quiz quiz;
}
