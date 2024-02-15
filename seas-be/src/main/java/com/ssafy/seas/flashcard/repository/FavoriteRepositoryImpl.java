package com.ssafy.seas.flashcard.repository;

import static com.ssafy.seas.category.entity.QCategory.*;
import static com.ssafy.seas.flashcard.entity.QFavorite.*;
import static com.ssafy.seas.flashcard.entity.QFlashcard.*;
import static com.ssafy.seas.flashcard.entity.QFlashcardContent.*;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.seas.flashcard.dto.FavoriteDto;
import com.ssafy.seas.flashcard.dto.FlashcardDto;
import com.ssafy.seas.flashcard.dto.QFlashcardDto_SimpleResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class FavoriteRepositoryImpl implements FavoriteRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<FavoriteDto.CardIdPerCategory> findAllFavoriteFlashcardIdsByMemberId(Integer memberId) {

		List<Tuple> result = queryFactory.select(
				category.id, category.name, flashcard.id
			)
			.from(favorite)
			.where(favorite.member.id.eq(memberId))
			.innerJoin(flashcard)
			.on(favorite.flashcard.id.eq(flashcard.id))
			.rightJoin(category)
			.on(flashcard.category.id.eq(category.id))
			.fetch();

		log.info(result.toString());

		List<FavoriteDto.CardIdPerCategory> resultDto = result.stream()
			.collect(Collectors.groupingBy(
				tuple -> tuple.get(category.id),
				Collectors.mapping(
					tuple -> new AbstractMap.SimpleEntry<>(

						tuple.get(category.name),
						tuple.get(flashcard.id)
					),
					Collectors.toList()
				)
			))
			.entrySet().stream()
			.map(entry -> new FavoriteDto.CardIdPerCategory(
				entry.getKey(),
				entry.getValue().get(0).getKey(),
				entry.getValue().stream()
					.map(AbstractMap.SimpleEntry::getValue)
					.collect(Collectors.toList())
			))
			.collect(Collectors.toList());

		return resultDto;
	}
}
