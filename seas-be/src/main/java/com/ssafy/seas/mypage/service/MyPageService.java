package com.ssafy.seas.mypage.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.seas.member.dto.MemberDto;
import com.ssafy.seas.member.mapper.MemberMapper;
import com.ssafy.seas.member.repository.MemberRepository;
import com.ssafy.seas.member.util.MemberUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {
	private final MemberRepository memberRepository;
	private final MemberUtil memberUtil;
	private final MemberMapper memberMapper;

	@Transactional(readOnly = true)
	public MemberDto.MyInfoResponse getMyInfo() {
		MemberDto.Response memberDto = memberUtil.getLoginMemberDto();
		return memberMapper.MemberDtoResponseToMemberDtoMyInfoResponse(memberDto);
	}
}
