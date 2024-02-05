package com.ssafy.seas.flashcard.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.seas.flashcard.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
	Optional<Favorite> findByMemberIdAndFlashcardId(Integer memberId, Integer FlashcardId);
}