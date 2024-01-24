package com.ssafy.seas.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberDto {
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Post {
		private String memberId;
		private String password;
		private String name;
		private String email;
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {
		private String memberId;
		private String password;
		private String name;
		private String email;
		private Integer point;
	}
}
