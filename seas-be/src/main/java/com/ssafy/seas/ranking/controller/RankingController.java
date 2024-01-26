package com.ssafy.seas.ranking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.seas.common.constants.ErrorCode;
import com.ssafy.seas.common.dto.ApiResponse;
import com.ssafy.seas.ranking.service.RankingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
public class RankingController {
	private final RankingService rankingService;

	@GetMapping("/list")
	public ApiResponse<?> getRankingList() {
		try {
			rankingService.getRankers();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return ApiResponse.error(ErrorCode.SERVER_ERROR);
		}
	}

	// public ApiResponse<String> regist(@RequestBody MemberDto.Post memberDto) {
	// 	try {
	// 		return ApiResponse.success(SuccessCode.POST_SUCCESS, memberService.signup(memberDto));
	// 	} catch (Exception e) {
	// 		e.printStackTrace();
	// 		return ApiResponse.error(ErrorCode.SERVER_ERROR);
	// 	}
	// }
}
