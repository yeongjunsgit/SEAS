package com.ssafy.seas.quiz.repository;

import com.ssafy.seas.quiz.entity.Factor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FactorRepository extends JpaRepository<Factor, Integer> {

    Optional<Factor> findByMemberIdAndCardQuiz_Flashcard_Id(Integer memberId, Integer flashcardId);

}
