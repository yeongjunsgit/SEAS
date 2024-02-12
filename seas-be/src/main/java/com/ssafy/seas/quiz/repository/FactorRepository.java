package com.ssafy.seas.quiz.repository;

import java.util.Optional;

import com.ssafy.seas.quiz.entity.Factor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FactorRepository extends JpaRepository<Factor, Integer> {

    @Modifying
    @Query("UPDATE Factor f " +
            "SET f.ef = :ef, f.quizInterval = :interval " +
            "WHERE f.cardQuiz.quiz.id = :quizId AND f.member.id = :memberId")
    void updateFactor(@Param("ef") Double ef, @Param("interval") Double interval, @Param("quizId") Integer quizId, @Param("memberId") Integer memberId);

    Optional<Factor> findByMemberIdAndCardQuiz_Flashcard_Id(Integer memberId, Integer flashcardId);

}
