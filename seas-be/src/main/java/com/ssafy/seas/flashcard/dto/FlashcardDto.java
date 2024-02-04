package com.ssafy.seas.flashcard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FlashcardDto {
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {
		private Long id;
		private String keyword;
		private String content;
		private Boolean isFavorite = false;
	}

}
