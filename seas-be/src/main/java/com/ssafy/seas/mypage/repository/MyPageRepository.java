package com.ssafy.seas.mypage.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seas.category.dto.QCategoryDto_Simple;
import com.ssafy.seas.mypage.dto.MyPageDto;
import com.ssafy.seas.mypage.dto.QMyPageDto_CorrectCount;
import com.ssafy.seas.mypage.dto.QMyPageDto_IncorrectNoteInfo;
import com.ssafy.seas.quiz.dto.IncorrectNoteDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.seas.category.entity.QCategory.category;
import static com.ssafy.seas.quiz.entity.QIncorrectNote.incorrectNote;
import static com.ssafy.seas.quiz.entity.QQuiz.quiz;
import static com.ssafy.seas.quiz.entity.QQuizAnswer.quizAnswer;
import static com.ssafy.seas.quiz.entity.QSolvedQuiz.solvedQuiz;

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

	public List<IncorrectNoteDto.QuizIdPerCategory> findAllIncorrectQuizByMemberId(Integer memberId) {
		QCategoryDto_Simple categoryDtoSimple = new QCategoryDto_Simple(category.id, category.name);

		List<Tuple> result = queryFactory.select(
				categoryDtoSimple, quiz.id
			)
			.from(incorrectNote)
			.where(incorrectNote.member.id.eq(memberId))
			.innerJoin(quiz)
			.on(incorrectNote.quiz.id.eq(quiz.id))
			.rightJoin(category)
			.on(quiz.category.id.eq(category.id))
			.orderBy(category.id.asc(), quiz.id.asc())
			.fetch();

		log.info(result.toString());

		List<IncorrectNoteDto.QuizIdPerCategory> resultDto = result.stream()
			.collect(Collectors.groupingBy(
				tuple -> tuple.get(categoryDtoSimple),
				Collectors.mapping(
					tuple -> tuple.get(quiz.id),
					Collectors.toList()
				)
			))
			.entrySet().stream()
			.map(entry -> new IncorrectNoteDto.QuizIdPerCategory(
				entry.getKey().getId(),
				entry.getKey().getName(),
				entry.getValue())
			)
			.collect(Collectors.toList());

		return resultDto;

	}

	public MyPageDto.IncorrectNoteInfo getAnswersWithQuizId(Integer quizId){

		return queryFactory.
				select(new QMyPageDto_IncorrectNoteInfo(quiz.problem, quizAnswer.answer))
				.from(quizAnswer)
				.join(quiz).on(quiz.id.eq(quizAnswer.quiz.id))
				.where(quiz.id.eq(quizId))
				.fetchFirst();
	}

}
