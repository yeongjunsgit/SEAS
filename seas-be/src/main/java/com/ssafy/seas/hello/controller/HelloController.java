package com.ssafy.seas.hello.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.seas.common.constants.SuccessCode;
import com.ssafy.seas.common.dto.ApiResponse;

@RestController
public class HelloController {

	@GetMapping("/hello")
	public ApiResponse<String> hello() {
		String message = "Hello World!";
		return ApiResponse.success(SuccessCode.GET_SUCCESS, message);
	}
}
