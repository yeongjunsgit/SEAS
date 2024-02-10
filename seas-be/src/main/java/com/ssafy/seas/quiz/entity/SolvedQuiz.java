package com.ssafy.seas.quiz.entity;

import com.ssafy.seas.common.entity.BaseEntity;
import com.ssafy.seas.member.entity.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SolvedQuiz extends BaseEntity {
	int correctCount = 0;
	int failedCount = 0;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "quiz_id")
	private Quiz quiz;

	public SolvedQuiz(Member member, Quiz quiz){
		this.member = member;
		this.quiz = quiz;
	}

	public void updateCount(boolean isRight){
		if(isRight) correctCount += 1;
		else failedCount += 1;
	}

}
