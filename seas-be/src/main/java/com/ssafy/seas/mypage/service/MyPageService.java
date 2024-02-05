package com.ssafy.seas.mypage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.seas.category.dto.CategoryDto;
import com.ssafy.seas.category.util.CategoryUtil;
import com.ssafy.seas.member.dto.MemberDto;
import com.ssafy.seas.member.repository.MemberRepository;
import com.ssafy.seas.member.util.MemberUtil;
import com.ssafy.seas.mypage.dto.MyPageDto;
import com.ssafy.seas.mypage.repository.MyPageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {
	private final MemberRepository memberRepository;
	private final MyPageRepository myPageRepository;
	private final MemberUtil memberUtil;
	private final CategoryUtil categoryUtil;

	public MemberDto.MyInfoResponse getMyInfo() {
		Integer memberId = memberUtil.getLoginMemberId();
		MemberDto.MyInfoResponse response = memberRepository.getMyInfoResponse(memberId);
		return response;
	}

	public List<MyPageDto.QuizRate> getQuizRate() {
		Integer memberId = memberUtil.getLoginMemberId();
		List<CategoryDto.Response> categories = categoryUtil.getCategories();
		List<MyPageDto.CorrectCount> correctCounts = myPageRepository.getQuizCorrectCountPerCategory(memberId);
		List<MyPageDto.QuizRate> quizRate = new ArrayList<>();

		int correctCountIndex = 0;
		int categoryId = -1;

		for (CategoryDto.Response category : categories) {
			if (correctCountIndex < correctCounts.size()) {
				categoryId = correctCounts.get(correctCountIndex).getCategoryId();
			}

			Double correctCount = categoryId == category.getId() ?
				Double.valueOf(correctCounts.get(correctCountIndex++).getCorrectCount()) : 0.0;
			Double quizCount = Double.valueOf(category.getQuizCount());
			Double rate = quizCount != 0 ? Math.round(correctCount / quizCount * 100 * 10) / 10.0 : 0.0;

			quizRate.add(MyPageDto.QuizRate.builder()
				.categoryId(category.getId())
				.categoryName(category.getName())
				.rate(rate)
				.build());
		}
		return quizRate;
	}

	public List<MyPageDto.PerformanceGraph> getQuizPerformanceGraph() {
		Integer memberId = memberUtil.getLoginMemberId();
		List<CategoryDto.Response> categories = categoryUtil.getCategories();
		List<MyPageDto.ScoreHistoryDetail> scoreHistoryDetails = myPageRepository.getScoreHistory(memberId);
		List<MyPageDto.PerformanceGraph> result = new ArrayList<>();

		int scoreHistoryIndex = 0;

		for (CategoryDto.Response category : categories) {
			MyPageDto.PerformanceGraph data = MyPageDto.PerformanceGraph.builder()
				.categoryId(category.getId())
				.categoryName(category.getName())
				.build();

			while (scoreHistoryIndex < scoreHistoryDetails.size()
				&& category.getId().equals(scoreHistoryDetails.get(scoreHistoryIndex).getCategoryId())) {
				MyPageDto.ScoreHistoryDetail score = scoreHistoryDetails.get(scoreHistoryIndex++);
				data.getHistory()
					.add(MyPageDto.ScoreHistory.builder()
						.createdAt(score.getCreatedAt())
						.score(score.getScore())
						.round(score.getRound())
						.build());
			}
			result.add(data);
		}
		return result;
	}
}
