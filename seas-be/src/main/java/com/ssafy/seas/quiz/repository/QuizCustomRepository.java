package com.ssafy.seas.quiz.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seas.quiz.dto.QQuizDto_QuizFactorDto;
import com.ssafy.seas.quiz.dto.QuizAnswerDto;
import com.ssafy.seas.quiz.dto.QuizDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.ssafy.seas.mypage.entity.QStreak.streak;
import static com.ssafy.seas.quiz.entity.QCardQuiz.cardQuiz;
import static com.ssafy.seas.quiz.entity.QFactor.factor;
import static com.ssafy.seas.quiz.entity.QIncorrectNote.incorrectNote;
import static com.ssafy.seas.quiz.entity.QQuiz.quiz;
import static com.ssafy.seas.quiz.entity.QQuizAnswer.quizAnswer;
import static com.ssafy.seas.quiz.entity.QScoreHistory.scoreHistory;
import static com.ssafy.seas.quiz.entity.QSolvedQuiz.solvedQuiz;


@Repository
@Slf4j
@RequiredArgsConstructor
public class QuizCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

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

    public void updateFailQuizState(QuizAnswerDto.UpdatedFactors newFactors){

    // 잔디, 성적, 멤버 히스토리도 해야함

        log.info("UPDATE FAIL QUIZ STATE : " + newFactors.toString());

        try {
            jpaQueryFactory
                    .update(factor)
                    .set(factor.ef, newFactors.getEf())
                    .set(factor.quizInterval, newFactors.getInterval())
                    .where(factor.cardQuiz.quiz.id.eq(newFactors.getQuizId()))
                    .execute();

            jpaQueryFactory
                    .update(incorrectNote)
                    .set(incorrectNote.quiz.id, newFactors.getQuizId())
                    .set(incorrectNote.member.id, newFactors.getMemberId())
                    .execute();

            jpaQueryFactory
                    .update(solvedQuiz)
                    .set(solvedQuiz.failedCount, solvedQuiz.failedCount.add(1))
                    .where(solvedQuiz.member.id.eq(newFactors.getMemberId()).and(solvedQuiz.quiz.id.eq(newFactors.getQuizId())))
                    .execute();

        }
        catch (Exception e){
            log.info(e.getMessage());
        }
    }

    public void updateCorrectQuizState(QuizAnswerDto.UpdatedFactors newFactors){

        log.info("UPDATE CORRECT QUIZ STATE : " + newFactors.toString());

        try {

            // 성적 히스토리(꺾은선)
            jpaQueryFactory
                    .update(scoreHistory)
                    .set(scoreHistory.score, scoreHistory.score.add(newFactors.getScore()))
                    .where(scoreHistory.member.id.eq(newFactors.getMemberId()).and(scoreHistory.category.id.eq(newFactors.getCategoryId())))
                    .execute();


            // 스트릭 갱신
            jpaQueryFactory
                    .update(streak)
                    .set(streak.quizCount, streak.quizCount.add(1))
                    .set(streak.updatedAt, LocalDateTime.now())
                    .where(streak.member.id.eq(newFactors.getMemberId()))
                    .execute();

            entityManager.refresh(streak);


            // 가중치 요소 갱신
            jpaQueryFactory
                    .update(factor)
                    .set(factor.quizInterval, newFactors.getInterval())
                    .set(factor.ef, newFactors.getEf())
                    .where(solvedQuiz.member.id.eq(newFactors.getMemberId()).and(factor.cardQuiz.quiz.id.eq(newFactors.getQuizId())))
                    .execute();

            entityManager.refresh(factor);

            // 퀴즈 정답 횟수 테이블 갱신
            jpaQueryFactory.
                    update(solvedQuiz)
                    .set(solvedQuiz.failedCount, solvedQuiz.correctCount.add(1))
                    .where(solvedQuiz.member.id.eq(newFactors.getMemberId()).and(solvedQuiz.quiz.id.eq(newFactors.getQuizId())))
                    .execute();

        }
        catch (Exception e){
            log.info(e.getMessage());
        }
    }

//    public List<String> checkQuizState(Integer memberId){
//
//
//    }
}
