package com.ssafy.seas.member.repository;

import static com.ssafy.seas.member.entity.QMember.*;
import static com.ssafy.seas.ranking.entity.QTier.*;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seas.member.dto.MemberDto;
import com.ssafy.seas.member.dto.QMemberDto_MyInfoResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class MemberRepositoryImpl implements MemberRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public MemberDto.MyInfoResponse getMyInfoResponse(Integer memberId) {

		JPAQuery<MemberDto.MyInfoResponse> response = queryFactory.select(
				new QMemberDto_MyInfoResponse(member.nickname, member.point, tier.name, member.point,
					member.point))
			.from(member)
			.where(member.id.eq(memberId))
			.innerJoin(tier)
			.on(member.point.between(tier.minScore, tier.maxScore));

		return response.fetchOne();
	}
}
