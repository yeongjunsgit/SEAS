package com.ssafy.seas.category.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.ssafy.seas.category.dto.CategoryDto;
import com.ssafy.seas.category.entity.Category;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
	CategoryDto.Response CategoryToResponseDto(Category category);
}
