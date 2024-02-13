package com.ssafy.seas.member.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberDto {
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AuthRequest {
		@Setter
		private String memberId;
		private String password;

		public UsernamePasswordAuthenticationToken toAuthentication() {
			return new UsernamePasswordAuthenticationToken(memberId, password);
		}
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AuthResponse {
		private String memberId;
		private String grantType;
		private String accessToken;
		private String refreshToken;
	}

	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Post {
		private String memberId;
		private String password;
		private String nickname;
		private String email;
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class checkId {
		private String id;
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class checkIdResult {
		private Boolean isDuplicate;
	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response {
		private String memberId;
		private String password;
		private String nickname;
		private String email;
		private Integer point;
	}

	@NoArgsConstructor
	@Getter
	public static class MyInfoResponse {
		private String nickname;
		private Integer point;
		private String tier;
		private Integer solvedCount = 0;
		// 소수점 아래 1번째 자리까지 표시
		private Double correctRate = 0.0;

		@QueryProjection
		public MyInfoResponse(String nickname, Integer point, String tier) {
			this.nickname = nickname;
			this.point = point;
			this.tier = tier;
		}

		@QueryProjection
		public MyInfoResponse(Integer solvedCount, Double correctRate) {
			this.solvedCount = solvedCount != null ? solvedCount : 0;
			this.correctRate = correctRate != null ? Math.round(correctRate * 10) / 10.0 : 0.0;
		}

		@Builder
		public MyInfoResponse(String nickname, Integer point, String tier, Integer solvedCount, Double correctRate) {
			this(solvedCount, correctRate);

			this.nickname = nickname;
			this.point = point;
			this.tier = tier;
		}
	}
}
