package com.ssafy.seas.mypage.entity;

import com.ssafy.seas.common.entity.BaseEntity;
import com.ssafy.seas.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Streak extends BaseEntity {
	int quizCount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	public Streak(Member member, int quizCount){
		this.member = member;
		this.quizCount = quizCount;
	}

	public void setQuizCount(Integer quizCount){
		this.quizCount = quizCount;
	}

	@Override
	public String toString() {
		return "Streak{" +
				"quizCount=" + quizCount +
				", member=" + member +
				'}';
	}
}
