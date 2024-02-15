package com.ssafy.seas.member.repository;

import com.ssafy.seas.member.dto.MemberDto;

public interface MemberRepositoryCustom {

	MemberDto.MyInfoResponse getMyInfoResponse(Integer memberId);
}
