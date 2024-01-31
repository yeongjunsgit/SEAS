package com.ssafy.seas.mypage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.seas.category.dto.CategoryDto;
import com.ssafy.seas.category.util.CategoryUtil;
import com.ssafy.seas.member.dto.MemberDto;
import com.ssafy.seas.member.mapper.MemberMapper;
import com.ssafy.seas.member.repository.MemberRepository;
import com.ssafy.seas.member.util.MemberUtil;
import com.ssafy.seas.mypage.dto.MyPageDto;
import com.ssafy.seas.mypage.repository.MyPageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MyPageService {
	private final MemberRepository memberRepository;
	private final MyPageRepository myPageRepository;
	private final MemberUtil memberUtil;
	private final CategoryUtil categoryUtil;

	@Transactional(readOnly = true)
	public MemberDto.MyInfoResponse getMyInfo() {
		Integer memberId = memberUtil.getLoginMemberId();
		MemberDto.MyInfoResponse response = memberRepository.getMyInfoResponse(memberId);
		return response;
	}

	@Transactional(readOnly = true)
	public List <MyPageDto.QuizRate> getQuizRate() {
		Integer memberId = memberUtil.getLoginMemberId();
		List<CategoryDto.Response> categories = categoryUtil.getCategories();
		List <MyPageDto.CorrectCount> correctCount = myPageRepository.getQuizCorrectCountPerCategory(memberId);
		List<MyPageDto.QuizRate> quizRate = new ArrayList<>();





		return null;
	}
}
