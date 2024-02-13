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
		try {
			return ApiResponse.success(SuccessCode.POST_SUCCESS, memberService.signup(memberDto));
		} catch (Exception e) {
			e.printStackTrace();
			return ApiResponse.error(ErrorCode.SERVER_ERROR);
		}
	}

	@PostMapping("/signin")
	public ApiResponse<MemberDto.Response> login(@RequestBody MemberDto.Post memberDto) {
		try{
			log.info("로그인 시도 : {}", memberDto.getMemberId());
			MemberDto.Response member = memberService.signin(memberDto);
			log.info("로그인 결과 : {}", member.getMemberId());
			if(member.getMemberId() != null) {
				return ApiResponse.success(SuccessCode.POST_SUCCESS, member);
			} else {
				return ApiResponse.error(ErrorCode.MEMBER_NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ApiResponse.error(ErrorCode.SERVER_ERROR);
		}
	}

	@PostMapping("/check-id")
	public ApiResponse<MemberDto.checkIdResult> login(@RequestBody MemberDto.checkId memberId) {
		try{
			return ApiResponse.success(SuccessCode.POST_SUCCESS, memberService.isDuplicatedId(memberId.getId()));
		} catch (Exception e) {
			e.printStackTrace();
			return ApiResponse.error(ErrorCode.SERVER_ERROR);
		}
	}

}
