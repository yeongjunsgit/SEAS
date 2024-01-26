package com.ssafy.seas.category.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.seas.category.dto.CategoryDto;
import com.ssafy.seas.category.service.CategoryService;
import com.ssafy.seas.common.constants.SuccessCode;
import com.ssafy.seas.common.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CategoryController {
	private final CategoryService categoryService;

	@GetMapping("/category")
	public ApiResponse<List<CategoryDto.Response>> getCategories() {

		return ApiResponse.success(SuccessCode.GET_SUCCESS, categoryService.getCategories());
	}
}
