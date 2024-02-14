package com.ssafy.seas.member.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ssafy.seas.common.constants.ErrorCode;
import com.ssafy.seas.member.dto.MemberDto;
import com.ssafy.seas.member.entity.Member;
import com.ssafy.seas.member.mapper.MemberMapper;
import com.ssafy.seas.member.repository.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberUtil {
	private final MemberRepository memberRepository;
	private final MemberMapper memberMapper;



	public Integer getLoginMemberId() {
		// 현재 로그인한 유저의 id(pk)를 반환
		Member member = memberRepository.findByMemberId(SecurityContextHolder.getContext().getAuthentication().getName())
			.orElseThrow(() -> new RuntimeException("일치하는 사용자가 없습니다."));
		log.info("MemberUtil getLoginMemberId ::::::::: id '{}'", member.getId());
		return member.getId();
	}

	// TODO: getLoginMemberId 구현 이후 로직 최적화
	public MemberDto.Response getLoginMemberDto() {
		Integer id = getLoginMemberId();
		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND.getMessage()));
		return memberMapper.MemberToMemberDtoResponse(member);
	}

	// TODO: getLoginMemberId 구현 이후 로직 최적화
	public Member getLoginMember() {
		Integer id = getLoginMemberId();
		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND.getMessage()));
		return member;
	}

	public Member getMemberByNickname(String nickname) {
		Member member = memberRepository.findByNickname(nickname)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND_WITH_NICKNAME.getMessage()));
		return member;
	}
}
