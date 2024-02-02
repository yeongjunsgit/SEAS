package com.ssafy.seas.member.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.ssafy.seas.member.dto.MemberDto;
import com.ssafy.seas.member.entity.Member;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
	Member MemberDtoToMember(MemberDto.Post memberDto);

	MemberDto.Response MemberToMemberDtoResponse(Member member);

	MemberDto.MyInfoResponse MemberDtoResponseToMemberDtoMyInfoResponse(MemberDto.Response memberDtoResponse);

}

