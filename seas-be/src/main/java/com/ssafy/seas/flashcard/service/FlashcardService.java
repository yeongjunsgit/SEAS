package com.ssafy.seas.flashcard.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.seas.flashcard.dto.FlashcardDto;
import com.ssafy.seas.flashcard.entity.Flashcard;
import com.ssafy.seas.flashcard.mapper.FlashcardMapper;
import com.ssafy.seas.flashcard.repository.FlashcardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class FlashcardService {
	private final FlashcardRepository flashcardRepository;
	private final FlashcardMapper flashcardMapper;

	public List<FlashcardDto.Response> getFlashcaradsByCategoryName(String categoryName) {
		// TODO: 즐겨찾기 개발 후 isFavorite 내용 추가
		List<Flashcard> flashcards = flashcardRepository.findByCategory_Name(categoryName);
		// TODO: 가중치 순 정렬

		return flashcards
			.stream()
			.map(flashcardMapper::FlashcardToResponseDto)
			.toList();
	}
}
