package com.ssafy.seas.member.repository;

import static com.ssafy.seas.member.entity.QMember.*;
import static com.ssafy.seas.quiz.entity.QSolvedQuiz.*;
import static com.ssafy.seas.ranking.entity.QTier.*;
import static org.hibernate.internal.util.NullnessHelper.*;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seas.common.constants.ErrorCode;
import com.ssafy.seas.member.dto.MemberDto;
import com.ssafy.seas.member.dto.QMemberDto_MyInfoResponse;
import com.ssafy.seas.quiz.entity.QSolvedQuiz;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class MemberRepositoryImpl implements MemberRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override

	public MemberDto.MyInfoResponse getMyInfoResponse(Integer memberId) {

		MemberDto.MyInfoResponse info = queryFactory.select(
				new QMemberDto_MyInfoResponse(member.nickname, member.point, tier.name))
			.from(member)
			.where(member.id.eq(memberId))
			.innerJoin(tier)
			.on(member.point.between(tier.minScore, tier.maxScore))
			.fetchOne();

		// 0으로 나누기 방지
		// NumberExpression<Double> correctCountSum =  new CaseBuilder().when(solvedQuiz.correctCount.isNull())
		// 	.then(0.0)
		// 	.otherwise(solvedQuiz.correctCount.sum().doubleValue());

		NumberExpression<Integer> correctCountSum = solvedQuiz.correctCount.sum();

		// NumberExpression<Double> failedCountSum =  new CaseBuilder().when(solvedQuiz.failedCount.isNull())
		// 	.then(0.0)
		// 	.otherwise(solvedQuiz.failedCount.sum().doubleValue());
		//
		// NumberExpression<Double> correctRate =new CaseBuilder().when(correctCountSum.add(failedCountSum).eq(0.0))
		// 	.then(0.0)
		// 	.otherwise(correctCountSum.divide(correctCountSum.add(failedCountSum)).multiply(100));

		MemberDto.MyInfoResponse stat  = queryFactory.select(new QMemberDto_MyInfoResponse(solvedQuiz.count().intValue(), 3.0)
			.from(solvedQuiz)
			.where(solvedQuiz.member.id.eq(memberId))
			.f0etchOne();

		if (info == null){
			log.error(ErrorCode.MEMBER_NOT_FOUND.getMessage());
			throw new EntityNotFoundException(ErrorCode.MEMBER_NOT_FOUND.getMessage());
		}

		return MemberDto.MyInfoResponse.builder()
			.nickname(info.getNickname())
			.point(info.getPoint())
			.tier(info.getTier())
			.solvedCount(stat.getSolvedCount())
			.correctRate(stat.getCorrectRate())
			.build();
	}
}
