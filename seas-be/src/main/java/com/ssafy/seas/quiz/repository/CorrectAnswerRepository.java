package com.ssafy.seas.quiz.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seas.member.entity.Member;
import com.ssafy.seas.mypage.entity.Streak;
import com.ssafy.seas.quiz.dto.QuizDto;
import com.ssafy.seas.quiz.entity.CardQuiz;
import com.ssafy.seas.quiz.entity.Factor;
import com.ssafy.seas.quiz.entity.Quiz;
import com.ssafy.seas.quiz.entity.SolvedQuiz;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ServerErrorException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.ssafy.seas.category.entity.QCategory.*;
import static com.ssafy.seas.member.entity.QMember.member;
import static com.ssafy.seas.mypage.entity.QStreak.streak;
import static com.ssafy.seas.quiz.entity.QCardQuiz.cardQuiz;
import static com.ssafy.seas.quiz.entity.QFactor.factor;
import static com.ssafy.seas.quiz.entity.QQuiz.*;
import static com.ssafy.seas.quiz.entity.QSolvedQuiz.solvedQuiz;

@Repository
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CorrectAnswerRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    @Transactional
    public void saveOrUpdateStreakAndSolvedQuiz(QuizDto.QuizFactorDto factors, Integer memberId) {

        // Member와 Streak을 조회
        // Fix : 한국 시간대로 한다고 하네요 미국 시간대로 고칩시다.
        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        Tuple result = jpaQueryFactory
                .select(member, streak)
                .from(member)
                .leftJoin(streak).on(member.id.eq(streak.member.id).and((streak.updatedAt.between(startDateTime, endDateTime))))
                .where(member.id.eq(memberId))
                .fetchOne();

        Member existingMember = result.get(0, Member.class);
        Streak existingStreak = result.get(1, Streak.class);

        if (existingStreak == null) {
            Member member = entityManager.getReference(Member.class, memberId);
            Streak newStreak = new Streak(member, 1);
            entityManager.persist(newStreak);
        }
        else { // Member와 Streak가 있는 경우, 값을 업데이트
            //Member existingMember = result.get(0, Member.class);
            Integer quizCount = existingStreak.getQuizCount();
            log.info(existingStreak.toString());
            existingStreak.setQuizCount(quizCount + 1);

            entityManager.merge(existingStreak);
        }

        // solveQuiz 검사 후 맞은 흔적이 없으면 갱신 -> solveQuiz는 날짜를 따지지 않는다.
        SolvedQuiz todaySolvedQuiz = jpaQueryFactory
                .select(solvedQuiz)
                .from(solvedQuiz)
                .where(solvedQuiz.member.id.eq(memberId)
                        .and(solvedQuiz.quiz.id.eq(factors.getQuizId())))
                .fetchOne();

        // 오늘 문제를 푼 흔적이 없다면
        if (todaySolvedQuiz == null) {
            // 맞힌 문제 = 1을 가진 새로운 레코드를 넣는다.

            Quiz quiz = entityManager.getReference(Quiz.class, factors.getQuizId());
            // solvedQuiz 생성자 생성

            Member member = entityManager.getReference(Member.class, memberId);
            SolvedQuiz solvedQuiz = new SolvedQuiz(member, quiz, 1, 0);
            entityManager.persist(solvedQuiz);

            Integer memberPoint = existingMember.getPoint();
            existingMember.setPoint(memberPoint + factors.getPoint());

            entityManager.merge(existingMember);
            log.info("member POINT : {} || GET POINT : {}", memberPoint, factors.getPoint());
        } else { // 오늘 문제를 푼 흔적이 있다면
            if (todaySolvedQuiz.getCorrectCount() == 0) { // 문제를 한번도 맞추지 못했다면
                // 멤버 테이블 포인트 추가, SolvedQuiz 맞힌 문제 + 1

                Integer memberPoint = existingMember.getPoint();
                existingMember.setPoint(memberPoint + factors.getPoint());

                entityManager.merge(existingMember);

                log.info("member POINT : {} || GET POINT : {}", memberPoint, factors.getPoint());

            }
            Integer todaySolvedQuizCount = todaySolvedQuiz.getCorrectCount();
            todaySolvedQuiz.setCorrectCount(todaySolvedQuizCount + 1);
            log.info("count : {}", todaySolvedQuiz.getCorrectCount());
        }

        entityManager.flush();

    }


    @Transactional
    public void
    saveOrUpdateFactor(QuizDto.QuizFactorDto factors, Integer memberId) throws ServerErrorException{
            // Factor와 CardQuiz를 조인하여 값을 조회

//            String jpql = "SELECT f, cq FROM Factor f " +
//                    "JOIN f.cardQuiz cq " +
//                    "WHERE f.member.id = :memberId AND cq.quiz.id = :quizId";

        Integer quizId = factors.getQuizId();

        Tuple result = jpaQueryFactory
            .select(factor, cardQuiz)
            .from(factor)
            .join(factor.cardQuiz, cardQuiz)
            .where(factor.member.id.eq(memberId)
                    .and(cardQuiz.quiz.id.eq(quizId)))
            .fetchOne();


            // Factor 레코드가 없다면
        if(result == null) {
            Member member = entityManager.getReference(Member.class, memberId);

            String cardQuizJpql =
                    "SELECT cq " +
                            "FROM CardQuiz cq "+
                            "WHERE cq.quiz.id = :quizId";

            Query cardQuizQuery = entityManager.createQuery(cardQuizJpql, Object[].class);
            cardQuizQuery.setParameter("quizId", factors.getQuizId());

            Object[] cardq = (Object[]) cardQuizQuery.getSingleResult();
            CardQuiz cardQuiz = (CardQuiz) cardq[0];

            Factor newFactor = new Factor(member, cardQuiz, factors.getQuizInterval(), factors.getEf());

            entityManager.persist(newFactor);
        }
        else { // Factor 레코드가 있다면 업데이트
            Factor factor = result.get(0, Factor.class);
            CardQuiz cardQuiz = result.get(1, CardQuiz.class);

            factor.updateFactor(factors.getQuizInterval(), factors.getEf());
        }

        entityManager.flush();
    }

    public Long getCorrectCountPerCategoryByMemberIdAndCategoryId(Integer memberId, Integer categoryId) {
        return jpaQueryFactory
            .select(solvedQuiz.count())
            .from(solvedQuiz)
            .join(quiz).on(solvedQuiz.quiz.id.eq(quiz.id))
            .join(category).on(quiz.category.id.eq(category.id))
            .where(category.id.eq(categoryId)
                .and(solvedQuiz.member.id.eq(memberId))
                .and(solvedQuiz.correctCount.goe(1)))
            .fetchOne();
    }
}

