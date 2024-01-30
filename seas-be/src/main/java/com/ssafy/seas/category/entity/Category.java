package com.ssafy.seas.category.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Formula;

import com.ssafy.seas.common.entity.BaseEntity;
import com.ssafy.seas.flashcard.entity.Flashcard;
import com.ssafy.seas.quiz.entity.Quiz;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {
	private String name;
	private String backgroundColor;
	private String lineColor;

	@Formula("(select count(*) from quiz q where q.category_id = id)")
	private Integer quizCount = 0;

	@OneToMany(mappedBy = "category")
	private List<Flashcard> flashCards = new ArrayList<>();

	@OneToMany(mappedBy = "category")
	private List<Quiz> quizzes = new ArrayList<>();

}
