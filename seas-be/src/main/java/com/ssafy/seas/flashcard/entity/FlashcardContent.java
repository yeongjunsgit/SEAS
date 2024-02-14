package com.ssafy.seas.flashcard.entity;

import com.ssafy.seas.category.entity.Category;
import com.ssafy.seas.common.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FlashcardContent extends BaseEntity {
	private String content = "";

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "flashcard_id")
	private Flashcard flashcard;
}
