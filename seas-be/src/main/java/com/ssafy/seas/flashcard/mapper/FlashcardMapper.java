package com.ssafy.seas.flashcard.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.ssafy.seas.flashcard.dto.FlashcardDto;
import com.ssafy.seas.flashcard.entity.Flashcard;
import com.ssafy.seas.flashcard.entity.FlashcardContent;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FlashcardMapper {

	@Mapping(target = "isFavorite", source = "isFavorite")
	@Mapping(target = "contents", source = "contents")
	FlashcardDto.Response FlashcardToResponseDto(Flashcard flashcard, List<FlashcardContent> contents, Boolean isFavorite);

	default List<String> mapContents(List<FlashcardContent> contents) {
		return contents.stream()
			.map(FlashcardContent::getContent)
			.collect(Collectors.toList());
	}
}
