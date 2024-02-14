package com.ssafy.seas.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.seas.common.dto.ApiResponse;
import com.ssafy.seas.common.logging.DiscordNotifier;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
	private final DiscordNotifier discordNotifier;
	private final StringBuilder sb = new StringBuilder();
	@ExceptionHandler(Exception.class)
	protected final ResponseEntity<ApiResponse<String>> handleAllExceptions(Exception ex) {
		log.error("Exception 발생!", ex);
		sb.setLength(0);
		sb.append("🚨 Exception 발생! 🚨\n");
		sb.append(ExceptionUtil.exceptionToString(ex)).append("\n");
		discordNotifier.notify(sb.toString());
		ApiResponse<String> response = ApiResponse.error(HttpStatus.BAD_REQUEST, ex.getMessage());
		return ResponseEntity.badRequest().body(response);
	}
}
