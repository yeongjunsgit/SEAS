package com.ssafy.seas.member.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.seas.member.dto.MemberDto;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationFilterCustom extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
		throws AuthenticationException {

		try {
			MemberDto.Post member = new ObjectMapper().readValue(request.getInputStream(), MemberDto.Post.class);
			log.info("attemptAuthentication() ---> memberId : [{}], password : [{}]",
				member.getMemberId(),
				member.getPassword());

			return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
					member.getMemberId(),     // 아이디
					member.getPassword(),    // 비밀번호
					new ArrayList<>()       // 권한
				)
			);
		} catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {

	}
}
