package com.ssafy.seas.flashcard.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.ssafy.seas.flashcard.dto.FlashcardDto;
import com.ssafy.seas.flashcard.entity.Flashcard;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FlashcardMapper {
	FlashcardDto.Response FlashcardoResponseDto(Flashcard flashcard);
}
