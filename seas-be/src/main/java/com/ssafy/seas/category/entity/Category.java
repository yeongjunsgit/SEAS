package com.ssafy.seas.category.entity;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.seas.common.entity.BaseEntity;
import com.ssafy.seas.flashcard.entity.Flashcard;

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

	// TODO: Formula 사용 논의
	private Integer quizCount = 0;

	@OneToMany(mappedBy = "category")
	private List<Flashcard> flashCards = new ArrayList<>();

}
