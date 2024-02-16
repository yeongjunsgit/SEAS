package com.ssafy.seas.flashcard.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.seas.common.constants.ErrorCode;
import com.ssafy.seas.common.exception.CustomException;
import com.ssafy.seas.flashcard.dto.FavoriteDto;
import com.ssafy.seas.flashcard.dto.FlashcardDto;
import com.ssafy.seas.flashcard.entity.Favorite;
import com.ssafy.seas.flashcard.entity.Flashcard;
import com.ssafy.seas.flashcard.mapper.FlashcardMapper;
import com.ssafy.seas.flashcard.repository.FavoriteRepository;
import com.ssafy.seas.flashcard.repository.FlashcardRepository;
import com.ssafy.seas.member.entity.Member;
import com.ssafy.seas.member.util.MemberUtil;
import com.ssafy.seas.quiz.constant.EasinessFactor;
import com.ssafy.seas.quiz.constant.Interval;
import com.ssafy.seas.quiz.constant.Quality;
import com.ssafy.seas.quiz.entity.CardQuiz;
import com.ssafy.seas.quiz.entity.Factor;
import com.ssafy.seas.quiz.repository.FactorRepository;
import com.ssafy.seas.quiz.util.CardQuizUtil;
import com.ssafy.seas.quiz.util.QuizUtil;

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
	private final FactorRepository factorRepository;

	private final FlashcardMapper flashcardMapper;
	private final MemberUtil memberUtil;
	private final CardQuizUtil cardQuizUtil;

	public List<FlashcardDto.Response> getFlashcardsByCategoryId(Integer categoryId) {
		Integer MemberId = memberUtil.getLoginMember().getId();
		List<FlashcardDto.Response> flashcards = flashcardRepository.findAllFlashcardsByMemberIdAndCategoryId(MemberId, categoryId);
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
			.orElseThrow(() -> new CustomException(ErrorCode.FLASHCARD_NOT_FOUND.getMessage()));
	}

	public List<FavoriteDto.CardIdPerCategory> getFavoriteFlashcardIds() {
		Member member = memberUtil.getLoginMember();
		List<FavoriteDto.CardIdPerCategory> favorites = favoriteRepository.findAllFavoriteFlashcardIdsByMemberId(member.getId());
		return  favorites;
	}

	@Transactional
	public FlashcardDto.Response patchWeight(FlashcardDto.Patch patchDto) {
		Flashcard flashcard = getFlashcardById(patchDto.getFlashcardId());
		Member member = memberUtil.getLoginMember();
		Optional<Favorite> existFavorite = favoriteRepository.findByMemberIdAndFlashcardId(member.getId(),
			flashcard.getId());

		Optional<Factor> existFactor = factorRepository.findByMemberIdAndCardQuiz_Flashcard_Id(member.getId(),
			flashcard.getId());

		Factor factor = null;
		if (existFactor.isPresent()) {
			factor = existFactor.get();
		} else {
			CardQuiz cardQuiz = cardQuizUtil.findByFlashcardId(flashcard.getId());
			factor = Factor.builder()
				.member(member)
				.cardQuiz(cardQuiz)
				.quizInterval(Interval.DEFAULT.getValue())
				.ef(EasinessFactor.DEFAULT.getValue())
				.build();
		}

		Double newInterval = QuizUtil.calculateNewInterval(factor.getQuizInterval(), factor.getEf());
		Double newEf = QuizUtil.calculateNewEf(factor.getEf(), Quality.FLASHCARD_VIEW.getValue());
		factor.updateFactor(newInterval, newEf);

		factorRepository.save(factor);


		return flashcardMapper.FlashcardToResponseDto(flashcard, flashcard.getFlashcardContents(),
			existFavorite.isPresent());
	}
}
