package com.ssafy.seas.common.dto;

import org.springframework.http.HttpStatus;

import com.ssafy.seas.common.constants.ErrorCode;
import com.ssafy.seas.common.constants.SuccessCode;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
	private final Integer status;
	private final String message;
	private T data;

	private ApiResponse(int status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	private ApiResponse(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public static <T> ApiResponse<T> success(SuccessCode code, T data) {
		return new ApiResponse<>(code.getStatus().value(), code.getMessage(), data);
	}

	public static <T> ApiResponse<T> error(ErrorCode code) {
		return new ApiResponse<>(code.getStatus().value(), code.getMessage());
	}

	public static ApiResponse<String> error(HttpStatus status, String message) {
		return new ApiResponse<>(status.value(), message, "");
	}
}