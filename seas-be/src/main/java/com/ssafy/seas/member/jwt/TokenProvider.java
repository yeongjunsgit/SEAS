package com.ssafy.seas.member.jwt;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ssafy.seas.member.dto.MemberDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenProvider {
	private static final String AUTHORITIES_KEY = "Authentication";
	private static final String BEARER_TYPE = "Bearer ";
	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 60;
	private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24 * 7;
	private final Key key;

	public TokenProvider(@Value("${secretKeyPlain}") String secretKey) {
	// public TokenProvider() {
		System.out.println("TokenProvider 생성자 짜잔 !!");
		// System.out.println("secretKey : " + secretKey);

		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);

		// SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
		// this.key = secretKey;
	}

	public MemberDto.AuthResponse generateTokenResponse(Authentication auth) {
		long now = new Date().getTime();

		String accessToken = Jwts.builder()
			.setSubject(auth.getName())
			.setExpiration(new Date(now + ACCESS_TOKEN_EXPIRE_TIME))
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();

		String refreshToken = Jwts.builder()
			.setSubject(auth.getName())
			.setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();

		System.out.println("generateTokenResponse start =============");
		System.out.println("access : " + accessToken.toString());
		System.out.println("refresh : " + refreshToken.toString());
		System.out.println("generateTokenResponse end ===============");

		return MemberDto.AuthResponse.builder()
			.memberId(auth.getName())
			.grantType(BEARER_TYPE)
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	public Authentication getAuthentication(String accessToken) {
		Claims claims = paresClaims(accessToken);

		// if (claims.get(AUTHORITIES_KEY) == null) {
		// 	throw new RuntimeException();
		// }

		UserDetails principal = new User(claims.getSubject(), "", new ArrayList<>());

		return new UsernamePasswordAuthenticationToken(principal, "");
	}

	public boolean validateToken(String token) {
		try {
			// Refresh Token 파싱되면 OK
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (SecurityException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
				 IllegalArgumentException e) {
			throw new RuntimeException(e);
		}
	}

	private Claims paresClaims(String accessToken) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
}
