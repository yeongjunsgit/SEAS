package com.ssafy.seas.member.entity;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.seas.common.entity.BaseEntity;
import com.ssafy.seas.flashcard.entity.Favorite;
import com.ssafy.seas.mypage.entity.Streak;
import com.ssafy.seas.quiz.entity.IncorrectNote;
import com.ssafy.seas.quiz.entity.Quiz;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "member")
public class Member extends BaseEntity {
	@Column(name = "member_id", nullable = false, unique = true)
	private String memberId;
	@Column(name = "pwd", nullable = false)
	private String password;
	@Column(nullable = false)
	private String nickname;
	@Column(nullable = false, unique = true)
	private String email;
	private Integer point = 0;

	@OneToMany(mappedBy = "member")
	private List<Favorite> favorites = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<Streak> streaks = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<IncorrectNote> incorrectNotes = new ArrayList<>();
}
