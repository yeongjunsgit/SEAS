package com.ssafy.seas.member.util;

import org.springframework.stereotype.Component;

import com.ssafy.seas.common.constants.ErrorCode;
import com.ssafy.seas.member.dto.MemberDto;
import com.ssafy.seas.member.entity.Member;
import com.ssafy.seas.member.mapper.MemberMapper;
import com.ssafy.seas.member.repository.MemberRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberUtil {
	private final MemberRepository memberRepository;
	private final MemberMapper memberMapper;

	public static Integer getLoginMemberId() {
		// TODO: 현재 로그인한 유저의 memberId 반환하는 로직 구현
		return 1;
	}

	// TODO: getLoginMemberId 구현 이후 로직 최적화
	public MemberDto.Response getLoginMemberDto() {
		Integer id = getLoginMemberId();
		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND.getMessage()));
		return memberMapper.MemberToMemberDtoResponse(member);
	}
}
