package com.ssafy.seas.member.service;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.seas.member.dto.MemberDto;
import com.ssafy.seas.member.entity.Member;
import com.ssafy.seas.member.jwt.TokenProvider;
import com.ssafy.seas.member.mapper.MemberMapper;
import com.ssafy.seas.member.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
	private final MemberRepository memberRepository;
	private final MemberMapper memberMapper;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final TokenProvider tokenProvider;

	@Transactional
	public String signup(MemberDto.Post memberDto) {
		memberRepository.save(memberMapper.MemberDtoToMember(memberDto));
		return "";
	}

	@Transactional
	public MemberDto.AuthResponse signin(MemberDto.AuthRequest memberDto) {
		Member member = memberRepository.findByMemberId(memberDto.getMemberId())
			.orElseThrow(() -> new UsernameNotFoundException("일치하는 사용자가 존재하지 않습니다."));
		Authentication authentication = authenticationManagerBuilder.getObject()
			.authenticate(memberDto.toAuthentication());

		MemberDto.AuthResponse authResponse = tokenProvider.generateTokenResponse(authentication);
		log.info("Authentication : {}", authentication.toString());
		// Todo : Token 저장
		return authResponse;
	}

	// @Transactional
	// public MemberDto.Response signin(MemberDto.Post memberDto) {
	// 	Member member = memberRepository.findByMemberIdAndPassword(memberDto.getMemberId(), memberDto.getPassword());
	//
	// 	// UserDetailsService ?
	// 	if(member == null) {
	// 		return new MemberDto.Response();
	// 	}
	//
	//
	//
	// 	// Authentication authentication = authenticationManagerBuilder.getObject().authenticate(memberDto.toAuthentication());
	//
	// 	return memberMapper.MemberToMemberDtoResponse(member);
	// }
}
