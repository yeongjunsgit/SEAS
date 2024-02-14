package com.ssafy.seas.member.service;

import java.util.Optional;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.seas.member.dto.MemberDto;
import com.ssafy.seas.member.entity.Member;
import com.ssafy.seas.member.jwt.TokenProvider;
import com.ssafy.seas.member.mapper.MemberMapper;
import com.ssafy.seas.member.repository.MemberRepository;
import com.ssafy.seas.member.util.MemberUtil;
import com.ssafy.seas.member.util.TokenUtil;

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
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final TokenUtil tokenUtil;
	private final MemberUtil memberUtil;

	@Transactional
	public String signup(MemberDto.Post memberDto) {
		String encodePassword = bCryptPasswordEncoder.encode(memberDto.getPassword());
		log.info("encodePassword : {}", encodePassword);
		memberDto.setPassword(encodePassword);
		memberRepository.save(memberMapper.MemberDtoToMember(memberDto));
		return "";
	}

	@Transactional
	public MemberDto.AuthResponse signin(MemberDto.AuthRequest memberDto) {
		Member member = memberRepository.findByMemberId(memberDto.getMemberId())
			.orElseThrow(() -> new UsernameNotFoundException("일치하는 사용자가 존재하지 않습니다."));
		log.info("로그인 시도한 멤버 :::::::::: {}, {}", member.getId(), member.getPassword());
		Authentication authentication = authenticationManagerBuilder.getObject()
			.authenticate(memberDto.toAuthentication());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		MemberDto.AuthResponse authResponse = tokenProvider.generateTokenResponse(authentication);
		log.info("Authentication : {}", authentication.toString());

		// Refresh Token Redis에 저장
		tokenUtil.setRefreshToken(authResponse.getRefreshToken());

		return authResponse;
	}

	public MemberDto.AuthResponse reIssue(MemberDto.AuthResponse tokenRequest) {
		// Refresh Token 파싱되면 OK
		tokenProvider.validateToken(tokenRequest.getRefreshToken());
		// Access Token 파싱해서 새로운 인증객체 만들기
		Authentication authentication = tokenProvider.getAuthentication(tokenRequest.getAccessToken());
		// Redis에 저장되어있는 Refresh Token과 Request로 받은 Refresh Token 비교
		if(tokenUtil.checkRefreshTokenEquals(tokenRequest.getRefreshToken()) == false){
			throw new RuntimeException("저장되어 있는 토큰과 일치하지 않습니다 !!!");
		}
		// 인증 객체로 토큰 재발행
		MemberDto.AuthResponse authResponse = tokenProvider.generateTokenResponse(authentication);

		return authResponse;
	}

	public String findMemberNicknameByMemberid(String memberId) {
		Member member = memberRepository.findByMemberId(memberId)
			.orElseThrow(() -> new RuntimeException("일치하는 사용자가 없습니다 !!!"));
		return memberMapper.MemberToMemberDtoResponse(member)
			.getNickname();
	}

	/**
	 * 로그인 id 중복 검사
	 * @param memberId : 로그인할 때 쓰는 아이디
	 * @return boolean 중복 여부. true면 중복(가입불가), false면 중복 없음 (가입 가능)
	 */
	public MemberDto.checkIdResult isDuplicatedId(String memberId) {
		Optional<Member> member = memberRepository.findByMemberId(memberId);
		if (member.isPresent()) {
			return new MemberDto.checkIdResult(true);
		}
		return new MemberDto.checkIdResult(false);

	}
}
