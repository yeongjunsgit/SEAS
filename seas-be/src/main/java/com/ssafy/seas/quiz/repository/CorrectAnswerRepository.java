package com.ssafy.seas.quiz.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seas.category.entity.Category;
import com.ssafy.seas.member.entity.Member;
import com.ssafy.seas.mypage.entity.Streak;
import com.ssafy.seas.quiz.dto.QuizAnswerDto;
import com.ssafy.seas.quiz.entity.*;
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
import java.util.Arrays;

import static com.ssafy.seas.quiz.entity.QSolvedQuiz.solvedQuiz;

@Repository
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CorrectAnswerRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    public void saveOrUpdateStreakAndScoreHistory(QuizAnswerDto.UpdatedFactors factors) {

        // Member와 Streak을 조회
        LocalDateTime startDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        String jpql = "SELECT m, s FROM Member m " +
                "LEFT JOIN Streak s ON m.id = s.member.id AND s.updatedAt BETWEEN :startDateTime AND :endDateTime " +
                //"LEFT JOIN ScoreHistory sh ON m.id = sh.member.id AND sh.category.id = :categoryId " +
                "WHERE m.id = :memberId";

        Member member = entityManager.getReference(Member.class, factors.getMemberId());

        Query query = entityManager.createQuery(jpql, Object[].class);
        query.setParameter("memberId", factors.getMemberId());
        query.setParameter("startDateTime", startDateTime);
        query.setParameter("endDateTime", endDateTime);

        Object[] result = (Object[]) query.getSingleResult();

        log.info("RESULT : " + Arrays.stream(result).count());
        log.info("RESULT : " + Arrays.stream(result).findFirst());
        Member existingMember = (Member) result[0];
        Streak existingStreak = (Streak) result[1];

        // Member나 Streak가 없는 경우에는 새로운 엔티티 생성
        if (existingStreak == null) {
            Streak newStreak = new Streak(member, 1);
            entityManager.persist(newStreak);

        }
        else { // Member와 Streak가 있는 경우, 값을 업데이트
            Integer quizCount = existingStreak.getQuizCount();
            log.info(existingStreak.toString());
            existingStreak.setQuizCount(quizCount + 1);

            entityManager.merge(existingStreak);
        }

        // scoreHistory는 무조건 들어간다.
        Category category = entityManager.getReference(Category.class, factors.getCategoryId());
        ScoreHistory newScoreHistory = new ScoreHistory(member, category, factors.getScore());
        entityManager.persist(newScoreHistory);

        // solveQuiz 검사 후 해당 날짜에 맞은 흔적이 없으면 갱신
        SolvedQuiz todaySolvedQuiz = jpaQueryFactory
                .select(solvedQuiz)
                .from(solvedQuiz)
                .where(solvedQuiz.updatedAt.between(startDateTime, endDateTime)
                        .and(solvedQuiz.member.id.eq(factors.getMemberId()))
                        .and(solvedQuiz.quiz.id.eq(factors.getQuizId())))
                .fetchOne();

        // 오늘 문제를 푼 흔적이 없다면
        if(todaySolvedQuiz == null){
            // 맞힌 문제 = 1을 가진 새로운 레코드를 넣는다.

            Quiz quiz = entityManager.getReference(Quiz.class, factors.getQuizId());
            // solvedQuiz 생성자 생성
            SolvedQuiz solvedQuiz = new SolvedQuiz(member, quiz, 1, 0);
            entityManager.persist(solvedQuiz);
        }
        else{ // 오늘 문제를 푼 흔적이 있다면
            if(todaySolvedQuiz.getCorrectCount() == 0) { // 문제를 한번도 맞추지 못했다면
                // 멤버 테이블 포인트 추가, SolvedQuiz 맞힌 문제 + 1
                Integer point = existingMember.getPoint();
                existingMember.setPoint(point + factors.getPoint());
            }
            Integer todaySolvedQuizCount = todaySolvedQuiz.getCorrectCount();
            todaySolvedQuiz.setCorrectCount(todaySolvedQuizCount + 1);
            log.info("count : {}", todaySolvedQuiz.getCorrectCount());
        }
    }


    public void
    saveOrUpdateFactor(QuizAnswerDto.UpdatedFactors factors, Integer memberId) throws ServerErrorException{
            // Factor와 CardQuiz를 조인하여 값을 조회

            String jpql = "SELECT f, cq FROM Factor f " +
                    "JOIN f.cardQuiz cq " +
                    "WHERE f.member.id = :memberId AND cq.quiz.id = :quizId";

            Query factorQuery = entityManager.createQuery(jpql, Object[].class);
            factorQuery.setParameter("memberId", factors.getMemberId());
            factorQuery.setParameter("quizId", factors.getQuizId());

            // Factor 레코드가 없다면
            if(factorQuery.getResultList().isEmpty()) {
                Member member = entityManager.getReference(Member.class, factors.getMemberId());

                String cardQuizJpql =
                        "SELECT cq " +
                                "FROM CardQuiz cq "+
                                "WHERE cq.quiz.id = :quizId";

                Query cardQuizQuery = entityManager.createQuery(cardQuizJpql, Object[].class);
                cardQuizQuery.setParameter("quizId", factors.getQuizId());

                Object[] result = (Object[]) cardQuizQuery.getSingleResult();
                CardQuiz cardQuiz = (CardQuiz) result[0];

                Factor newFactor = new Factor(member, cardQuiz, factors.getInterval(), factors.getEf());

                entityManager.persist(newFactor);

                entityManager.flush();
            }
            else { // Factor 레코드가 있다면 업데이트
                Object[] result = (Object[]) factorQuery.getSingleResult();
                Factor factor = (Factor) result[0];

                factor.updateFactor(factors.getInterval(), factors.getEf());
            }
    }
}

