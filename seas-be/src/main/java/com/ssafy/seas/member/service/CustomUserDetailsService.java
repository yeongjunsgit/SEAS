package com.ssafy.seas.member.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ssafy.seas.member.entity.Member;
import com.ssafy.seas.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
	private final MemberRepository memberRepository;

	/**
	 *
	 * @param username 사용자 로그인 Id
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member findMember = memberRepository.findByMemberId(username)
			.orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다."));
		// return null;
		// return new User(
		// findMember.getMemberId(),
		// findMember.getPassword(),
		// new ArrayList<>()
		// );
		UserDetails userDetails = new User(
			findMember.getMemberId(),
			findMember.getPassword(),
			new ArrayList<>()
		);

		log.info("userDetails : {}", userDetails.toString());

		return userDetails;
	}
}
