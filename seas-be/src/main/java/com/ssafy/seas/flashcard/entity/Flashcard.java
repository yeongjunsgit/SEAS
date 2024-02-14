package com.ssafy.seas.flashcard.entity;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.seas.category.entity.Category;
import com.ssafy.seas.common.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Flashcard extends BaseEntity {
	private String keyword = "";

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@OneToMany(mappedBy = "flashcard")
	private List<FlashcardContent> flashcardContents = new ArrayList<>();
}
