package com.ssafy.seas.flashcard.dto;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class FavoriteDto {

	@Getter
	@AllArgsConstructor
	public static class CardIdPerCategory {
		private Integer categoryId;
		private String categoryName;
		private List<Integer> flashcardIds = new ArrayList<>();
	}
}
