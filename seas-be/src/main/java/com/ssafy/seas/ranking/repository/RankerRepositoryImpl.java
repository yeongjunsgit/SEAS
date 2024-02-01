package com.ssafy.seas.ranking.repository;

// QClass는 static import를 한다.

import static com.ssafy.seas.member.entity.QMember.*;
import static com.ssafy.seas.ranking.entity.QTier.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seas.ranking.dto.QRankerDto_RankResponse;
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
		// List<RankerDto.Response> result = entityManager.createQuery(
		// 	"select new com.ssafy.seas.ranking.dto.RankerDto.Response("
		// 		+ "m.memberId, m.nickname, (select t.name "
		// 		+ 						   "from Tier t "
		// 		+ 						   "where m.point between t.minScore and t.maxScore)) "
		// 		+ "from Member m"
		// ).getResultList();

		return queryFactory
			.select(new QRankerDto_RankResponse(member.nickname, member.point, tier.name))
			.from(member)
			.innerJoin(tier)
			.on(member.point.between(tier.minScore, tier.maxScore))
			.orderBy(member.point.desc())
			.limit(10)
			.fetch();
	}


	@Override
	public List<RankerDto.RankResponse> getMyRank(String uuid) {
		// List<RankerDto.Response> result = entityManager.createQuery(
		// 	"select new com.ssafy.seas.ranking.dto.RankerDto.Response("
		// 		+ "m.memberId, m.nickname, (select t.name "
		// 		+ 						   "from Tier t "
		// 		+ 						   "where m.point between t.minScore and t.maxScore)) "
		// 		+ "from Member m "
		// 		+ "where m.memberId = :uuid"
		// ).setParameter("uuid", uuid).getResultList();

		return queryFactory
			.select(new QRankerDto_RankResponse(member.nickname, member.point, tier.name))
			.from(member)
			.where(member.memberId.eq(uuid))
			.innerJoin(tier)
			.on(member.point.between(tier.minScore, tier.maxScore))
			.fetch();
	}







	// 이거는 Bean 주입으로 해도 되고 이렇게 해도 된다.
	// 대신 이렇게 하면 다 이걸 써 줘야 한다.
	// public RankerRepositoryImpl(EntityManager em) {
	// 	this.queryFactory = new JPAQueryFactory(em);
	// }

	/*
	@Autowired
	private EntityManager entityManager;


	@Override
	public List<RankerDto.Response> getRankers() {
		List<RankerDto.Response> result = entityManager.createQuery(
			"select new com.ssafy.seas.ranking.dto.RankerDto.Response("
				+ "m.memberId, m.nickname, (select t.name "
				+ 						   "from Tier t "
				+ 						   "where m.point between t.minScore and t.maxScore)) "
				+ "from Member m"
		).getResultList();

		return result;
	}

	@Override
	public List<RankerDto.Response> getMyRank(String uuid) {
		List<RankerDto.Response> result = entityManager.createQuery(
			"select new com.ssafy.seas.ranking.dto.RankerDto.Response("
				+ "m.memberId, m.nickname, (select t.name "
				+ 						   "from Tier t "
				+ 						   "where m.point between t.minScore and t.maxScore)) "
				+ "from Member m "
				+ "where m.memberId = :uuid"
		).setParameter("uuid", uuid).getResultList();

		return result;
	}
*/

	/*
	public RankerDto.Response searchMemberRank(String nickname) {
		RankerDto.Response result = entityManager.createQuery(
			""
		)
	}
*/





	// 페이징 1 - 100
	// 랭킹은 보통 RDBMS를 안 쓴다.
	// 캐싱을 해서 사용하거나 랭킹 정보를 저장한다.
	// @Override
	// public List<RankerDto.Response> getRankers() {
	// 	return queryFactory
	// 		.select(new QRankerDto_Response(member.nickname, member.memberId, member.email))
	// 		.from(member)
	// 		.fetch();
	// }
}
