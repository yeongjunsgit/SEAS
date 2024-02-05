package com.ssafy.seas.flashcard.repository;

import java.util.List;

import com.ssafy.seas.flashcard.dto.FlashcardDto;

public interface FlashcardRepositoryCustom {
	List<FlashcardDto.Response> findAllFlashcardsByMemberIdAndCategoryId(Integer memberId, Integer categoryId);
}
