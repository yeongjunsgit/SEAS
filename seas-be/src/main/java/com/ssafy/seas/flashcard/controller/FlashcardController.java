package com.ssafy.seas.flashcard.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.seas.common.constants.SuccessCode;
import com.ssafy.seas.common.dto.ApiResponse;
import com.ssafy.seas.flashcard.dto.FlashcardDto;
import com.ssafy.seas.flashcard.service.FlashcardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FlashcardController {
	private final FlashcardService flashcardService;

	@GetMapping("/flashcard")
	public ApiResponse<List<FlashcardDto.Response>> getFlashcards(@RequestParam("category") String categoryName) {
		// TODO: categoryName 검증
		
		return ApiResponse.success(SuccessCode.GET_SUCCESS,
			flashcardService.getFlashcaradsByCategoryName(categoryName));
	}
}
