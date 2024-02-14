package com.ssafy.seas.member.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.seas.common.constants.ErrorCode;
import com.ssafy.seas.common.constants.SuccessCode;
import com.ssafy.seas.common.dto.ApiResponse;
import com.ssafy.seas.member.dto.MemberDto;
import com.ssafy.seas.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberController {
	private final MemberService memberService;

	@PostMapping("/signup")
	public ApiResponse<String> regist(@RequestBody MemberDto.Post memberDto) {
		return ApiResponse.success(SuccessCode.POST_SUCCESS, memberService.signup(memberDto));
	}

	@PostMapping("/signin")
	public ApiResponse<MemberDto.AuthResponse> login(@RequestBody MemberDto.AuthRequest memberDto) {
		log.info("로그인 시도 : {}", memberDto.getMemberId());
		MemberDto.AuthResponse member = memberService.signin(memberDto);
		log.info("로그인 결과 : {}", member.getMemberId());

		if (member.getMemberId() != null) {
			return ApiResponse.success(SuccessCode.POST_SUCCESS, member);
		} else {
			return ApiResponse.error(ErrorCode.MEMBER_NOT_FOUND);
		}
	}

	@PostMapping("/check-id")
	public ApiResponse<MemberDto.checkIdResult> login(@RequestBody MemberDto.checkId memberId) {
		return ApiResponse.success(SuccessCode.POST_SUCCESS, memberService.isDuplicatedId(memberId.getId()));
	}

	@PostMapping("/refresh")
	public ApiResponse<MemberDto.AuthResponse> reIssue(@RequestBody MemberDto.AuthResponse tokenRequest) {
		log.info("Refresh Token 재발행 시작 !!!!!!!!!!");
		MemberDto.AuthResponse authResponse = memberService.reIssue(tokenRequest);
		return ApiResponse.success(SuccessCode.POST_SUCCESS, authResponse);
	}
}
