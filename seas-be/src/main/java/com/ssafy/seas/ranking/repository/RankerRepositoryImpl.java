package com.ssafy.seas.ranking.repository;

// QClass는 static import를 한다.
import static com.ssafy.seas.member.entity.QMember.*;
import static com.ssafy.seas.ranking.entity.QBadge.*;
import static com.ssafy.seas.ranking.entity.QMemberBadge.*;
import static com.ssafy.seas.ranking.entity.QTier.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seas.ranking.dto.BadgeDto;
import com.ssafy.seas.ranking.dto.QBadgeDto_BadgeResponse;
import com.ssafy.seas.ranking.dto.QRankerDto_RankResponse;
import com.ssafy.seas.ranking.dto.QRankerDto_RankResponseWithRanking;
import com.ssafy.seas.ranking.dto.RankerDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Repository
@Transactional
@RequiredArgsConstructor
// ~~RepositoryImpl은 Spring Naming Comvention 이다.
// 어기면 @Repository를 해놔도 인식을 못하므로 빌드 에러가 뜬다.
public class RankerRepositoryImpl implements RankerRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public List<RankerDto.RankResponse> getRankers() {
		return queryFactory
			.select(
				new QRankerDto_RankResponse(
						member.nickname,
						member.point,
						tier.name
						))
			.from(member)
			.innerJoin(tier)
			.on(member.point.between(tier.minScore, tier.maxScore))
			.orderBy(member.point.desc())
			.limit(10)
			.fetch();
	}

	@Override
	public List<BadgeDto.BadgeResponse> getBadgeList(String nickname){
		return queryFactory
			.select(
				new QBadgeDto_BadgeResponse(
					badge.id,
					badge.name
				))
			.from(memberBadge)
			.innerJoin(badge)
			.on(memberBadge.badgeId.eq(badge.id))
			.innerJoin(member)
			.on(memberBadge.memberId.eq(member.id))
			.where(member.nickname.eq(nickname))
			.orderBy(badge.id.asc())
			.fetch();
	}


	@Override
	public List<RankerDto.RankResponse> getMyRank(String uuid) {
		return queryFactory
			.select(new QRankerDto_RankResponse(member.nickname, member.point, tier.name))
			.from(member)
			.where(member.memberId.eq(uuid))
			.innerJoin(tier)
			.on(member.point.between(tier.minScore, tier.maxScore))
			.fetch();
	}


	public List<RankerDto.RankResponseWithRanking> getRankByNickname(String nickname) {
		List<RankerDto.RankResponseWithRanking> list = queryFactory
			.select(
				new QRankerDto_RankResponseWithRanking(
					member.nickname,
					member.point,
					tier.name,
					Expressions.numberTemplate(Integer.class, "(RANK() OVER (ORDER BY {0} DESC))", member.point).as("ranking")
					))
			.from(member)
			.innerJoin(tier)
			.on(member.point.between(tier.minScore, tier.maxScore))
			.fetch();

		List<RankerDto.RankResponseWithRanking> result = new ArrayList<>();

		for(RankerDto.RankResponseWithRanking dto : list){
			if(dto.getNickname().equals(nickname)){
				result.add(dto);
				break;
			}
		}

		return result;
	}

	@Override
	public List<BadgeDto.BadgeResponse> getBadgeListByMemberId(Integer memberId) {
		return queryFactory
			.select(
				new QBadgeDto_BadgeResponse(
					badge.id,
					badge.name
				))
			.from(member)
			.where(member.id.eq(memberId))
			.innerJoin(memberBadge)
			.on(memberBadge.memberId.eq(member.id))
			.innerJoin(badge)
			.on(memberBadge.badgeId.eq(badge.id))
			.orderBy(badge.id.asc())
			.fetch();
	}

}
