package com.ssafy.seas.flashcard.repository;

import static com.querydsl.core.group.GroupBy.*;
import static com.ssafy.seas.flashcard.entity.QFavorite.*;
import static com.ssafy.seas.flashcard.entity.QFlashcard.*;
import static com.ssafy.seas.flashcard.entity.QFlashcardContent.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seas.flashcard.dto.FlashcardDto;
import com.ssafy.seas.flashcard.dto.QFlashcardDto_Response;
import com.ssafy.seas.flashcard.dto.QFlashcardDto_SimpleResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class FlashcardRepositoryImpl implements FlashcardRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<FlashcardDto.Response> findAllFlashcardsByMemberIdAndCategoryId(Integer memberId, Integer categoryId) {

		ComparableExpression<Boolean> isFavorite = Expressions.cases()
			.when(favorite.isNotNull()).then(Boolean.TRUE)
			.otherwise(Boolean.FALSE).as("isFavorite");

		QFlashcardDto_SimpleResponse flashcardDtoSimpleResponse = new QFlashcardDto_SimpleResponse(
			flashcard.id,
			flashcard.keyword,
			isFavorite
		);

		List<Tuple> result = queryFactory.select(
				flashcardDtoSimpleResponse,
				flashcardContent.content)
			.from(flashcard)
			.leftJoin(flashcardContent)
			.on(flashcard.id.eq(flashcardContent.flashcard.id))
			.fetchJoin()
			.leftJoin(favorite)
			.on(favorite.flashcard.id.eq(flashcard.id).and(favorite.member.id.eq(memberId)))
			.where(flashcard.category.id.eq(categoryId))
			.fetch();

		Map<FlashcardDto.SimpleResponse, List<String>> dtoMap = result.stream()
			.collect(Collectors.groupingBy(
				tuple -> tuple.get(flashcardDtoSimpleResponse),
				Collectors.mapping(tuple -> tuple.get(flashcardContent.content), Collectors.toList())
			));

		List<FlashcardDto.Response> dtos = dtoMap
			.entrySet().stream()
			.map(entry -> new FlashcardDto.Response(
				entry.getKey().getId(),
				entry.getKey().getKeyword(),
				entry.getValue(),
				entry.getKey().getIsFavorite()
			))
			.collect(Collectors.toList());

		return dtos;
	}
}
