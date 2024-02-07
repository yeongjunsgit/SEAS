package com.ssafy.seas.category.dto;

import java.util.Objects;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CategoryDto {
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {
		private Integer id;
		private String name;
		private String backgroundColor;
		private String lineColor;
		private Integer quizCount;
	}

	@Getter
	public static class Simple {
		private Integer id;
		private String name;

		@QueryProjection
		public Simple(Integer id, String name) {
			this.id = id;
			this.name = name;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			Simple simple = (Simple)o;
			return Objects.equals(id, simple.id) && Objects.equals(name, simple.name);
		}

		@Override
		public int hashCode() {
			return Objects.hash(id, name);
		}
	}

}
