package com.ssafy.seas.quiz.repository;

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

@Repository
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CorrectAnswerRepository {

    private final EntityManager entityManager;

    // streak은 똑같은 문제도 맞다고 인정해주나?
    public void saveOrUpdateStreakAndScoreHistory(QuizAnswerDto.UpdatedFactors factors) {
        // Member와 Streak, ScoreHistory를 조인하여 값을 조회, 날짜 기준 오늘
        String jpql = "SELECT m, s, sh FROM Member m " +
                "LEFT JOIN Streak s ON m.id = s.member.id AND s.createdAt = CURRENT_DATE " +
                "LEFT JOIN ScoreHistory sh ON m.id = sh.member.id AND sh.category.id = :categoryId AND sh.createdAt = CURRENT_DATE " +
                "WHERE m.id = :memberId";

        Member member = entityManager.getReference(Member.class, factors.getMemberId());
        Category category = entityManager.getReference(Category.class, factors.getCategoryId());

        Query query = entityManager.createQuery(jpql, Object[].class);
        query.setParameter("memberId", factors.getMemberId());
        query.setParameter("categoryId", factors.getCategoryId());

        Object[] result = (Object[]) query.getSingleResult();
        Member existingMember = (Member) result[0];
        Streak existingStreak = (Streak) result[1];
        ScoreHistory existingScoreHistory = (ScoreHistory) result[2];

        if (existingStreak == null) {
            // Member나 Streak가 없는 경우에는 새로운 엔티티 생성
            Streak newStreak = new Streak(member, 1);
            entityManager.persist(newStreak);
        } else {
            // Member와 Streak가 있는 경우, 값을 업데이트
            Integer quizCount = existingStreak.getQuizCount();
            existingStreak.setQuizCount(quizCount + 1);
        }

        if (existingScoreHistory == null) {
            // Member나 ScoreHistory가 없는 경우에는 새로운 엔티티 생성
            ScoreHistory newScoreHistory = new ScoreHistory(member, category, factors.getScore());
            entityManager.persist(newScoreHistory);
        } else {
            // Member와 ScoreHistory가 있는 경우, 값을 업데이트
            Integer score = existingScoreHistory.getScore();
            existingScoreHistory.setScore(score + factors.getScore());
        }

        Integer point = existingMember.getPoint();
        existingMember.setPoint(point + factors.getPoint());
    }


    public void saveOrUpdateFactorAndSolvedQuiz(QuizAnswerDto.UpdatedFactors factors) {
            // Factor와 CardQuiz를 조인하여 값을 조회
            String jpql = "SELECT f, cq FROM Factor f " +
                    "JOIN f.cardQuiz cq " +
                    "WHERE f.member.id = :memberId AND cq.quiz.id = :quizId";

            Query query = entityManager.createQuery(jpql, Object[].class);
            query.setParameter("memberId", factors.getMemberId());
            query.setParameter("quizId", factors.getQuizId());

            Object[] result = (Object[]) query.getSingleResult();
            Factor factor = (Factor) result[0];
            CardQuiz cardQuiz = (CardQuiz) result[1];

            if (factor == null || cardQuiz == null) {
                // Factor나 CardQuiz가 없는 경우에는 새로운 엔티티 생성
                Member member = entityManager.getReference(Member.class, factors.getMemberId());
                Quiz quiz = entityManager.getReference(Quiz.class, factors.getQuizId());

                CardQuiz newCardQuiz = new CardQuiz();
                newCardQuiz.setQuiz(quiz);

                Factor newFactor = new Factor(member, newCardQuiz, factors.getInterval(), factors.getEf());

                // SolvedQuiz 엔티티 생성 및 저장
                SolvedQuiz solvedQuiz = new SolvedQuiz(member, quiz);
                solvedQuiz.updateCount(true);

                entityManager.persist(newFactor);
                entityManager.persist(solvedQuiz);
            } else {
                // Factor와 CardQuiz가 있는 경우, 값을 업데이트
                factor.updateFactor(factors.getInterval(), factors.getEf());

                // 똑같은 퀴즈는 안 올라감
//                // SolvedQuiz 엔티티 가져오기
//                Quiz quiz = cardQuiz.getQuiz();
//                SolvedQuiz solvedQuiz = entityManager.createQuery("SELECT sq FROM SolvedQuiz sq WHERE sq.member = :member AND sq.quiz = :quiz", SolvedQuiz.class)
//                        .setParameter("member", factor.getMember())
//                        .setParameter("quiz", quiz)
//                        .getSingleResult();
//
//                // SolvedQuiz 엔티티 업데이트
//                solvedQuiz.updateCount(true);
            }
        }
    }

