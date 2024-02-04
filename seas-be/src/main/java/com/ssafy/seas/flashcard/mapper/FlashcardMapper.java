package com.ssafy.seas.flashcard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ssafy.seas.flashcard.dto.FlashcardDto;
import com.ssafy.seas.flashcard.entity.Flashcard;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FlashcardMapper {

	@Mapping(target = "isFavorite", source = "isFavorite")
	FlashcardDto.Response FlashcardToResponseDto(Flashcard flashcard, Boolean isFavorite);
}
