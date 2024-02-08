package com.ssafy.seas.quiz.repository;


import com.ssafy.seas.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {

    ///Optional<QuizAnswer> findByQuizId(Integer quizId);
    //Optional<>


}
