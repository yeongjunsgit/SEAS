package com.ssafy.seas.ranking.repository;

// QClass는 static import를 한다.

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

@Repository
// @RequiredArgsConstructor
// ~~RepositoryImpl은 Spring Naming Comvention 이다.
// 어기면 @Repository를 해놔도 인식을 못하므로 빌드 에러가 뜬다.
public class RankerRepositoryImpl implements RankerRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	// 이거는 Bean 주입으로 해도 되고 이렇게 해도 된다.
	// 대신 이렇게 하면 다 이걸 써 줘야 한다.
	public RankerRepositoryImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

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
