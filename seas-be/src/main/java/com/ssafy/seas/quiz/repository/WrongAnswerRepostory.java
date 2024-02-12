package com.ssafy.seas.quiz.repository;

import com.ssafy.seas.member.entity.Member;
import com.ssafy.seas.quiz.entity.IncorrectNote;
import com.ssafy.seas.quiz.entity.Quiz;
import com.ssafy.seas.quiz.entity.SolvedQuiz;
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

    public void saveOrUpdateIncorrectNoteAndSolvedQuiz(Integer memberId, Integer quizId) {
        // IncorrectNote와 SolvedQuiz 엔티티를 조인하여 값을 조회
        //
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
        }

        else {

            Object[] result = (Object[]) query.getSingleResult();
            IncorrectNote incorrectNote = (IncorrectNote) result[0];
            SolvedQuiz solvedQuiz = (SolvedQuiz) result[1];
            // 결과가 있으면 값 업데이트
            if (incorrectNote != null) {
                // 근데.. 이거 마지막꺼라며
                // updatedAt이라 괜춘하겠지?
                entityManager.merge(incorrectNote);
            }
            if (solvedQuiz != null) {
                // 업데이트 로직 추가
                solvedQuiz.updateCount(false);

                // 저장 또는 업데이트
                entityManager.merge(solvedQuiz);
            }
        }
    }
}
