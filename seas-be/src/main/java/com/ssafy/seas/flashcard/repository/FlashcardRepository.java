package com.ssafy.seas.flashcard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.seas.flashcard.entity.Flashcard;

public interface FlashcardRepository extends JpaRepository<Flashcard, Integer>, FlashcardRepositoryCustom {
	List<Flashcard> findByCategory_Name(String categoryName);
}