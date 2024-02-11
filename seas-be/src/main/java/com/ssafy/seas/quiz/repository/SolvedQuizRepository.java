package com.ssafy.seas.quiz.repository;

import com.ssafy.seas.quiz.entity.SolvedQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SolvedQuizRepository extends JpaRepository<SolvedQuiz, Long> {

    @Modifying
    @Query("UPDATE SolvedQuiz sq " +
            "SET sq.failedCount = sq.failedCount + 1 " +
            "WHERE sq.member.id = :memberId AND sq.quiz.id = :quizId")
    void updateSolvedQuizFailedCount(@Param("memberId") Integer memberId, @Param("quizId") Integer quizId);

}
