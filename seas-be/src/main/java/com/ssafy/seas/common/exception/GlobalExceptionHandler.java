package com.ssafy.seas.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.seas.common.dto.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	protected final ApiResponse<String> handleAllExceptions(Exception ex) {
		log.error("Exception", ex);
		return ApiResponse.error(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

}
