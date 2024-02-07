package com.ssafy.seas.mypage.mapper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.ssafy.seas.mypage.dto.StreakDto;
import com.ssafy.seas.mypage.entity.Streak;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StreakMapper {

	@Mapping(source = "quizCount", target = "grade", qualifiedByName = "quizCountToGrade")
		// @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "mapTimestampToLocalDateTime")
	StreakDto.Response StreakToResponseDto(Streak streak);

	@Named("quizCountToGrade")
	default int quizCountToGrade(Integer quizCount) {
		if (quizCount == null) {
			quizCount = 0;
		}

		int[] thresholds = {0, 10, 20, 30, 40};
		int index = Arrays.binarySearch(thresholds, quizCount);

		// Arrays.binarySearch() returns (-(insertion point) - 1) if the key is not found.
		if (index < 0) {
			index = -(index + 1);
		}

		if (index > 4) {
			index = 4;
		}

		return index;
	}
}
