package com.ssafy.seas.quiz.repository;

import com.ssafy.seas.member.entity.Member;
import com.ssafy.seas.quiz.dto.QuizAnswerDto;
import com.ssafy.seas.quiz.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class WrongAnswerRepostory {

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

            SolvedQuiz solvedQuiz = new SolvedQuiz(member, quiz);
            solvedQuiz.updateCount(false);
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
                solvedQuiz.updateCount(false);
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



            String updateFactor =
                    "UPDATE Factor f " +
                    "SET f.ef = :ef, f.quizInterval = :interval " +
                    "WHERE  = :quizId AND f.member.id = :memberId";

            Integer rows = entityManager.createNativeQuery(updateFactor)
                    .setParameter("ef", factors.getEf())
                    .setParameter("interval", factors.getInterval())
                    .setParameter("quizId", factors.getQuizId())
                    .setParameter("memberId", factors.getMemberId())
                    .executeUpdate();

            log.info(">>>>> SAVE FACTOR, 영향받은 행 수 : " , rows);

        }


    }
}
