package com.ssafy.seas.quiz.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seas.member.entity.Member;
import com.ssafy.seas.quiz.dto.QuizAnswerDto;
import com.ssafy.seas.quiz.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static com.ssafy.seas.quiz.entity.QFactor.factor;

@Repository
@Slf4j
@RequiredArgsConstructor
public class WrongAnswerRepostory {

    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;

    // 틀릴 때 factor가 안 들어감
    public void saveOrUpdateIncorrectNoteAndSolvedQuiz(Integer memberId, Integer quizId) {
        // IncorrectNote와 SolvedQuiz 엔티티를 조인하여 값을 조회

        String jpql = "SELECT innote, sq FROM IncorrectNote innote " +
                "LEFT JOIN SolvedQuiz sq ON innote.member.id = sq.member.id AND innote.quiz.id = sq.quiz.id " +
                "WHERE innote.member.id = :memberId AND innote.quiz.id = :quizId";

        // innote, solvedQuiz와

        Member member = entityManager.getReference(Member.class, memberId);
        Quiz quiz = entityManager.getReference(Quiz.class, quizId);

        Query query = entityManager.createQuery(jpql, Object[].class);
        query.setParameter("memberId", memberId);
        query.setParameter("quizId", quizId);

        if(query.getResultList().isEmpty()){

            IncorrectNote incorrectNote = new IncorrectNote(member, quiz);
            entityManager.persist(incorrectNote);

            // 틀린 횟수 + 1
            SolvedQuiz solvedQuiz = new SolvedQuiz(member, quiz, 0, 1);
            entityManager.persist(solvedQuiz);

            entityManager.flush();
        }

        else {
            Object[] result = (Object[]) query.getSingleResult();
            IncorrectNote incorrectNote = (IncorrectNote) result[0];
            SolvedQuiz solvedQuiz = (SolvedQuiz) result[1];
            // 결과가 있으면 값 업데이트
            if (incorrectNote != null) {
                entityManager.merge(incorrectNote);
            }
            if (solvedQuiz != null) {
                // 업데이트 로직 추가
                log.info("현재 SolvedQuiz correct :{}, fail :{}", solvedQuiz.getCorrectCount(), solvedQuiz.getFailedCount());
                Integer todayFailedSolvedQuiz = solvedQuiz.getFailedCount();
                solvedQuiz.setWrongCount(todayFailedSolvedQuiz + 1);
                log.info("현재 SolvedQuiz correct :{}, fail :{}", solvedQuiz.getCorrectCount(), solvedQuiz.getFailedCount());

                // 저장 또는 업데이트
                entityManager.merge(solvedQuiz);
                entityManager.flush();
            }
        }
    }

    public void saveOrUpdateFactor(Integer memberId, QuizAnswerDto.UpdatedFactors factors){
        String jpql = "SELECT COUNT(f) " +
                "FROM Factor f " +
                "WHERE f.cardQuiz.quiz.id = :quizId AND f.member.id = :memberId";


        Query query = entityManager.createQuery(jpql, Integer.class);
        query.setParameter("quizId", factors.getQuizId());
        query.setParameter("memberId", memberId);

        if((Long) query.getSingleResult() == 0) {
            Member member = entityManager.getReference(Member.class, memberId);
            Quiz quiz = entityManager.getReference(Quiz.class, factors.getQuizId());

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
        else { // Factor update

            log.info("현재 퀴즈 아이디, ef, interval : {}, {}, {} ", factors.getQuizId(), factors.getEf(), factors.getInterval());
            jpaQueryFactory
                    .update(factor)
                    .set(factor.ef, factors.getEf())
                    .set(factor.quizInterval, factors.getInterval())
                    .where(factor.cardQuiz.quiz.id.eq(factors.getQuizId())
                                    .and(factor.member.id.eq(factors.getMemberId())))
                    .execute();

        }
    }
}
