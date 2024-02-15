package com.ssafy.seas.category.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.seas.category.dto.CategoryDto;
import com.ssafy.seas.category.mapper.CategoryMapper;
import com.ssafy.seas.category.repository.CategoryRepository;
import com.ssafy.seas.category.util.CategoryUtil;
import com.ssafy.seas.common.constants.CacheConstant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CategoryService {
	private final CategoryUtil categoryUtil;

	public List<CategoryDto.Response> getCategories() {
		return categoryUtil.getCategories();
	}
}
