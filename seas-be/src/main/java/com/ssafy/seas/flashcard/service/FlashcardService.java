package com.ssafy.seas.flashcard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.seas.common.constants.ErrorCode;
import com.ssafy.seas.flashcard.dto.FlashcardDto;
import com.ssafy.seas.flashcard.entity.Favorite;
import com.ssafy.seas.flashcard.entity.Flashcard;
import com.ssafy.seas.flashcard.mapper.FlashcardMapper;
import com.ssafy.seas.flashcard.repository.FavoriteRepository;
import com.ssafy.seas.flashcard.repository.FlashcardRepository;
import com.ssafy.seas.member.entity.Member;
import com.ssafy.seas.member.util.MemberUtil;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class FlashcardService {
	private final FlashcardRepository flashcardRepository;
	private final FavoriteRepository favoriteRepository;
	private final FlashcardMapper flashcardMapper;
	private final MemberUtil memberUtil;

	public List<FlashcardDto.Response> getFlashcardsByCategoryId(Integer categoryId) {
		Integer MemberId = memberUtil.getLoginMember().getId();
		List<FlashcardDto.Response> flashcards = flashcardRepository.findAllFlashcardsByMemberIdAndCategoryId(MemberId, categoryId);
		// TODO: 가중치 순 정렬
		return flashcards;
	}

	public FlashcardDto.Response getFlashcaradByFlashcardId(Integer flashcardId) {
		Member member = memberUtil.getLoginMember();
		Flashcard flashcard = getFlashcardById(flashcardId);
		Optional<Favorite> existFavorite = favoriteRepository.findByMemberIdAndFlashcardId(member.getId(), flashcardId);
		boolean isFavorite = existFavorite.isPresent();
		return flashcardMapper.FlashcardToResponseDto(flashcard, flashcard.getFlashcardContents(),isFavorite);
	}

	@Transactional
	public FlashcardDto.Response postFavorite(Integer flashcardId) {
		Member member = memberUtil.getLoginMember();
		Flashcard flashcard = getFlashcardById(flashcardId);
		Optional<Favorite> existFavorite = favoriteRepository.findByMemberIdAndFlashcardId(member.getId(), flashcardId);
		if (existFavorite.isEmpty()) {
			Favorite favorite = Favorite.builder().member(member).flashcard(flashcard).build();
			favoriteRepository.save(favorite);
		}
		return flashcardMapper.FlashcardToResponseDto(flashcard, flashcard.getFlashcardContents(),true);
	}
	@Transactional
	public FlashcardDto.Response deleteFavorite(Integer flashcardId) {
		Member member = memberUtil.getLoginMember();
		Flashcard flashcard = getFlashcardById(flashcardId);
		Optional<Favorite> existFavorite = favoriteRepository.findByMemberIdAndFlashcardId(member.getId(), flashcardId);
		if (existFavorite.isPresent()) {
			favoriteRepository.delete(existFavorite.get());
		}
		return flashcardMapper.FlashcardToResponseDto(flashcard, flashcard.getFlashcardContents(),false);
	}

	private Flashcard getFlashcardById(Integer flashcardId) {
		return flashcardRepository.findById(flashcardId)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.FLASHCARD_NOT_FOUND.getMessage()));
	}

	public List<Integer> getFavoriteFlashcardIds() {
		Member member = memberUtil.getLoginMember();
		List<Favorite> favorites = favoriteRepository.findByMemberId(member.getId());
		return  favorites.stream().map(favorite -> favorite.getFlashcard().getId()).toList();

	}
}
