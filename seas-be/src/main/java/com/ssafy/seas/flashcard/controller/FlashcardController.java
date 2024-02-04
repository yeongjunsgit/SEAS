package com.ssafy.seas.flashcard.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.seas.category.dto.CategoryDto;
import com.ssafy.seas.category.service.CategoryService;
import com.ssafy.seas.common.constants.ErrorCode;
import com.ssafy.seas.common.constants.SuccessCode;
import com.ssafy.seas.common.dto.ApiResponse;
import com.ssafy.seas.flashcard.dto.FlashcardDto;
import com.ssafy.seas.flashcard.service.FlashcardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FlashcardController {
	private final FlashcardService flashcardService;
	private final CategoryService categoryService;

	@GetMapping("/flashcard")
	public ApiResponse<List<FlashcardDto.Response>> getFlashcards(@RequestParam("category") String categoryName) {
		List<CategoryDto.Response> categories = categoryService.getCategories();

		Optional<Integer> categoryId = categories.stream()
			.filter(category -> categoryName.equals(category.getName()))
			.map(category-> category.getId())
			.findFirst();

		// categoryName과 일치하는 카테고리가 없다면 NoSuchElementException 발생
		categoryId.orElseThrow(() -> new NoSuchElementException(ErrorCode.BAD_CATEGORY_NAME.getMessage()));

		return ApiResponse.success(SuccessCode.GET_SUCCESS,
			flashcardService.getFlashcaradsByCategoryId(categoryId.get()));
	}

	@GetMapping ("/flashcard/{flashcardId}")
	public ApiResponse<FlashcardDto.Response> getFlashcard(@PathVariable("flashcardId") Integer flashcardId) {
		return ApiResponse.success(SuccessCode.GET_SUCCESS,
			flashcardService.getFlashcaradByFlashcardId(flashcardId));
	}

	@PostMapping("/flashcard/{flashcardId}/favorite")
	public ApiResponse<FlashcardDto.Response> postFavorite(@PathVariable("flashcardId") Integer flashcardId) {
		return ApiResponse.success(SuccessCode.POST_SUCCESS,
			flashcardService.postFavorite(flashcardId));
	}

	@DeleteMapping("/flashcard/{flashcardId}/favorite")
	public ApiResponse<FlashcardDto.Response> deleteFavorite(@PathVariable("flashcardId") Integer flashcardId) {
		return ApiResponse.success(SuccessCode.DELETE_SUCCESS,
			flashcardService.deleteFavorite(flashcardId));
	}
}
