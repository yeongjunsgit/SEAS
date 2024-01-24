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

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberController {
	private final MemberService memberService;

	@PostMapping("/signup")
	public ApiResponse<String> regist(@RequestBody MemberDto memberDto){
		try{
			return ApiResponse.success(SuccessCode.GET_SUCCESS, memberService.signup(memberDto));
		} catch(Exception e) {
			e.printStackTrace();
			return ApiResponse.error(ErrorCode.SERVER_ERROR);
		}
	}



}
