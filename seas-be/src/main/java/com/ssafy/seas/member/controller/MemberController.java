package com.ssafy.seas.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.seas.common.constants.ErrorCode;
import com.ssafy.seas.common.constants.SuccessCode;
import com.ssafy.seas.member.dto.MemberDto;
import com.ssafy.seas.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
	private final MemberService memberService;

	@PostMapping("/regist")
	public ResponseEntity<String> regist(@RequestBody MemberDto memberDto){
		try{
			memberService.signup(memberDto);
			return new ResponseEntity<String>(SuccessCode.POST_SUCCESS.getMessage(), SuccessCode.POST_SUCCESS.getStatus());
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(ErrorCode.SERVER_ERROR.getMessage(), ErrorCode.SERVER_ERROR.getStatus());
		}
	}



}
