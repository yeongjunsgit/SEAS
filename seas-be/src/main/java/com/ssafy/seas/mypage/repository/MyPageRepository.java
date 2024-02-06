package com.ssafy.seas.mypage.repository;

import static com.ssafy.seas.category.entity.QCategory.*;
import static com.ssafy.seas.quiz.entity.QQuiz.*;
import static com.ssafy.seas.quiz.entity.QSolvedQuiz.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seas.mypage.dto.MyPageDto;
import com.ssafy.seas.mypage.dto.QMyPageDto_CorrectCount;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Repository
public class MyPageRepository {
	private final JPAQueryFactory queryFactory;
	private final EntityManager entityManager;

	public List<MyPageDto.CorrectCount> getQuizCorrectCountPerCategory(Integer memberId) {


		List<MyPageDto.CorrectCount> quizCorrectCountPerCategory = queryFactory.select(
				new QMyPageDto_CorrectCount(category.id, category.name, solvedQuiz.correctCount.count().intValue()))
			.from(solvedQuiz)
			.where(solvedQuiz.member.id.eq(memberId), solvedQuiz.correctCount.gt(0))
			.innerJoin(quiz).on(solvedQuiz.quiz.id.eq(quiz.id))
			.innerJoin(category).on(quiz.category.id.eq(category.id))
			.groupBy(category.id)
			.orderBy(category.id.asc())
			.fetch();

		log.info(quizCorrectCountPerCategory.toString());

		return quizCorrectCountPerCategory;

	}

	// 성적 추이 그래프
	public List<MyPageDto.ScoreHistoryDetail> getScoreHistory(Integer memberId) {
		String nativeQuery = "SELECT " +
			"id, " +
			"created_at AS CreatedAt, " +
			"score, " +
			"category_id AS categoryId, " +
			"member_id AS memberId, " +
			"round " +
			"FROM ( " +
			"   SELECT " +
			"       id, " +
			"       created_at, " +
			"       score, " +
			"       category_id, " +
			"       member_id, " +
			"       RANK() OVER (PARTITION BY category_id ORDER BY created_at DESC) as row_num, " +
			"       RANK() OVER (PARTITION BY category_id ORDER BY created_at ASC) as round " +
			"   FROM " +
			"       score_history " +
			"   WHERE " +
			"       member_id = :memberId" +
			") ranked " +
			"WHERE " +
			"    row_num <= 5 " +
			"ORDER BY category_id, round DESC";

		Query query = entityManager.createNativeQuery(nativeQuery, MyPageDto.ScoreHistoryDetail.class);
		query.setParameter("memberId", memberId);

		List<MyPageDto.ScoreHistoryDetail> result = query.getResultList();

		return result;
	}


}