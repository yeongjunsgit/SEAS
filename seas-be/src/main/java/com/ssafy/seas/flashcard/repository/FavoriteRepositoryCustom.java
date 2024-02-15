package com.ssafy.seas.flashcard.repository;

import java.util.List;

import com.ssafy.seas.flashcard.dto.FavoriteDto;

public interface FavoriteRepositoryCustom {
	List<FavoriteDto.CardIdPerCategory> findAllFavoriteFlashcardIdsByMemberId(Integer memberId);
}
