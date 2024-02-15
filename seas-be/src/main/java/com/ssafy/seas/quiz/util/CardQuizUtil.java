package com.ssafy.seas.quiz.util;

import org.springframework.stereotype.Component;

import com.ssafy.seas.common.constants.ErrorCode;
import com.ssafy.seas.common.exception.CustomException;
import com.ssafy.seas.quiz.entity.CardQuiz;
import com.ssafy.seas.quiz.repository.CardQuizRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class CardQuizUtil {
	private final CardQuizRepository cardQuizRepository;

	public CardQuiz findByFlashcardId(Integer flashcardId) {
		return cardQuizRepository.findByFlashcardId(flashcardId).orElseThrow(() -> new CustomException(
			ErrorCode.CARDQUIZ_NOT_FOUND_BY_FLASHCARD.getMessage()));
	}
}
