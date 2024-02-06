package com.ssafy.seas.quiz.entity;

import com.ssafy.seas.category.entity.Category;
import com.ssafy.seas.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz extends BaseEntity {
	String problem;
	String hint;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

//	@OneToOne
//	@JoinColumn(name="card_quiz_id")
//	private CardQuiz cardQuiz;

	@OneToMany(mappedBy = "quiz")
	private List<QuizAnswer> quizAnwers = new ArrayList<>();
}
