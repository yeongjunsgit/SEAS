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
	public ApiResponse<MemberDto.AuthResponse> login(@RequestBody MemberDto.AuthRequest memberDto) {
		// String secretKeyPlain = "아니spring너무어렵자나요이거맞니상속받는건너무많은데레퍼런스도바꼈고아주화가나";
		// // 키를 Base64 인코딩
		// String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
		// System.out.println("인코딩 된 키 : " + keyBase64Encoded);
		// // Base64 인코딩된 키를 이용하여 SecretKey 객체를 만든다.
		// SecretKey secretKey = Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
		// System.out.println("비밀키 객체 : " + secretKey.toString());



		try{
			log.info("로그인 시도 : {}", memberDto.getMemberId());
			MemberDto.AuthResponse member = memberService.signin(memberDto);
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

	@PostMapping("/refresh")
	public ApiResponse<MemberDto.AuthResponse> reIssue(@RequestBody MemberDto.AuthResponse tokenRequest) {
		log.info("Refresh Token 재발행 시작 !!!!!!!!!!");
		try{
			MemberDto.AuthResponse authResponse = memberService.reIssue(tokenRequest);
			return ApiResponse.success(SuccessCode.POST_SUCCESS, authResponse);
		} catch(Exception e) {
			e.printStackTrace();
			return ApiResponse.error(ErrorCode.SERVER_ERROR);
		}
	}

}
