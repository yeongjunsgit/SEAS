package com.ssafy.seas.flashcard.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.seas.flashcard.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer>, FavoriteRepositoryCustom {
	Optional<Favorite> findByMemberIdAndFlashcardId(Integer memberId, Integer FlashcardId);
	List<Favorite> findByMemberId(Integer memberId);
}