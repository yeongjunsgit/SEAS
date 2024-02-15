package com.ssafy.seas.category.util;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.seas.category.dto.CategoryDto;
import com.ssafy.seas.category.mapper.CategoryMapper;
import com.ssafy.seas.category.repository.CategoryRepository;
import com.ssafy.seas.common.constants.CacheConstant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
@Transactional(readOnly = true)
public class CategoryUtil {
	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;

	// getCategories 메서드의 결과가 "getCategories"라는 이름의 캐시에 저장
	@Cacheable(CacheConstant.CATEGORY_CACHE_NAME)
	public List<CategoryDto.Response> getCategories() {
		return categoryRepository.findAll().stream().map(categoryMapper::CategoryToResponseDto).toList();
	}
}
