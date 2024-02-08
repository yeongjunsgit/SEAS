package com.ssafy.seas.flashcard.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class FlashcardDto {
	@Getter
	public static class Response {
		private Integer id;
		private String keyword;
		private List<String> contents = new ArrayList<>();
		private Boolean isFavorite = false;

		@QueryProjection
		public Response(Integer id, String keyword, List<String> contents, Boolean isFavorite) {
			this.id = id;
			this.keyword = keyword;
			this.contents = contents;
			this.isFavorite = isFavorite;
		}
	}

	@Getter
	@NoArgsConstructor
	public static class Patch {
		private Integer flashcardId;
	}

	@ToString
	@Getter
	public static class SimpleResponse {
		private Integer id;
		private String keyword;
		private Boolean isFavorite = false;

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			SimpleResponse that = (SimpleResponse)o;
			return Objects.equals(id, that.id) && Objects.equals(keyword, that.keyword)
				&& Objects.equals(isFavorite, that.isFavorite);
		}

		@Override
		public int hashCode() {
			return Objects.hash(id, keyword, isFavorite);
		}

		@QueryProjection
		public SimpleResponse(Integer id, String keyword, Boolean isFavorite) {
			this.id = id;
			this.keyword = keyword;
			this.isFavorite = isFavorite;
		}
	}

}
