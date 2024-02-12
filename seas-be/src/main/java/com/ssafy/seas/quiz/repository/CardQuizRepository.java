package com.ssafy.seas.quiz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.seas.quiz.entity.CardQuiz;

public interface CardQuizRepository extends JpaRepository<CardQuiz, Integer> {
	Optional<CardQuiz> findByFlashcardId(Integer flashcardId);
}
