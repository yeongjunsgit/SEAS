package com.ssafy.seas.quiz.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seas.quiz.dto.QQuizDto_QuizFactorDto;
import com.ssafy.seas.quiz.dto.QuizDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.seas.quiz.entity.QCardQuiz.cardQuiz;
import static com.ssafy.seas.quiz.entity.QFactor.factor;
import static com.ssafy.seas.quiz.entity.QQuiz.quiz;
import static com.ssafy.seas.quiz.entity.QQuizAnswer.quizAnswer;


@Repository
@Slf4j
@RequiredArgsConstructor
public class QuizCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public List<QuizDto.QuizFactorDto> findAllQuizInnerJoin(Integer memberId, Integer categoryId) {

        List<QuizDto.QuizFactorDto> QuizInfoList =
                jpaQueryFactory
                .select(new QQuizDto_QuizFactorDto(factor.member.id, factor.cardQuiz.id, quiz.id, quiz.problem, quiz.hint, factor.quizInterval, factor.ef))
                .from(factor)
                .join(factor.cardQuiz).join(cardQuiz.quiz)
                .on(cardQuiz.isNotNull().and(factor.cardQuiz.id.eq(cardQuiz.id)))
                        .on(quiz.isNotNull().and(cardQuiz.quiz.id.eq(quiz.id)))
                        //.fetchJoin()
                .where(factor.member.id.eq(memberId.intValue()),
                        quiz.category.id.eq(categoryId.intValue())
                ).stream().toList();

        //log.info(QuizInfoList.toString());

        return QuizInfoList;
    }


    public List<String> findAllQuizAnswerByQuizId(Integer quizId){

        return jpaQueryFactory
                            .select(quizAnswer.answer)
                .from(quiz)
                .leftJoin(quizAnswer)
                .on(quiz.id.eq(quizAnswer.quiz.id))
                .where(quiz.id.eq(quizId))
                .fetch();

    }
}
