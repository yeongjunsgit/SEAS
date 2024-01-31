package com.ssafy.seas.mypage.repository;

import static com.ssafy.seas.category.entity.QCategory.*;
import static com.ssafy.seas.member.entity.QMember.*;
import static com.ssafy.seas.quiz.entity.QQuiz.*;
import static com.ssafy.seas.quiz.entity.QSolvedQuiz.*;
import static com.ssafy.seas.ranking.entity.QTier.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seas.member.dto.MemberDto;
import com.ssafy.seas.member.dto.QMemberDto_MyInfoResponse;
import com.ssafy.seas.mypage.dto.MyPageDto;
import com.ssafy.seas.mypage.dto.QMyPageDto_CorrectCount;
import com.ssafy.seas.quiz.entity.QSolvedQuiz;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Repository
public class MyPageRepository {
	private final JPAQueryFactory queryFactory;

	public List<MyPageDto.CorrectCount> getQuizCorrectCountPerCategory(Integer memberId) {

		List<MyPageDto.CorrectCount> quizCorrectCountPerCategory = queryFactory.select(
				new QMyPageDto_CorrectCount(category.id, category.name, solvedQuiz.correctCount.count().intValue()))
			.from(solvedQuiz)
			.where(solvedQuiz.member.id.eq(memberId), solvedQuiz.correctCount.gt(0))
			.innerJoin(quiz).on(solvedQuiz.quiz.id.eq(quiz.id))
			.innerJoin(category).on(quiz.category.id.eq(category.id))
			.groupBy(category.id, category.name)
			.fetch();

		log.info(quizCorrectCountPerCategory.toString());

		return quizCorrectCountPerCategory;

	}
}
