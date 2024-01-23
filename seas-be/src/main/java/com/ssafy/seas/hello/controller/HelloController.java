package com.ssafy.seas.hello.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.seas.common.dto.ApiResponse;

@RestController
public class HelloController {

	@GetMapping("/hello")
	public ApiResponse<String> hello() throws Exception {
		String returnData = "Hello World!";
		throw new Exception("exception입니당");
		// return ApiResponse.success(SuccessCode.GET_SUCCESS, returnData);
	}
}
