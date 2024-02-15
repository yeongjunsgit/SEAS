package com.ssafy.seas.mypage.service;


import com.ssafy.seas.category.dto.CategoryDto;
import com.ssafy.seas.category.util.CategoryUtil;
import com.ssafy.seas.member.dto.MemberDto;
import com.ssafy.seas.member.entity.Member;
import com.ssafy.seas.member.repository.MemberRepository;
import com.ssafy.seas.member.util.MemberUtil;
import com.ssafy.seas.mypage.dto.MyPageDto;
import com.ssafy.seas.mypage.dto.StreakDto;
import com.ssafy.seas.mypage.entity.Streak;
import com.ssafy.seas.mypage.mapper.StreakMapper;
import com.ssafy.seas.mypage.repository.MyPageRepository;
import com.ssafy.seas.mypage.repository.StreakRepository;
import com.ssafy.seas.quiz.dto.IncorrectNoteDto;
import com.ssafy.seas.ranking.dto.BadgeDto;
import com.ssafy.seas.ranking.repository.RankerRepositoryCustom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {
	private final MemberRepository memberRepository;
	private final MyPageRepository myPageRepository;
	private final RankerRepositoryCustom rankerRepository;
	private final StreakRepository streakRepository;
	private final MemberUtil memberUtil;
	private final CategoryUtil categoryUtil;
	private final StreakMapper streakMapper;

	public MemberDto.MyInfoResponse getMyInfo(String nickname) {
		Integer memberId = nickname != null ? memberUtil.getMemberByNickname(nickname).getId() : memberUtil.getLoginMemberId();
		MemberDto.MyInfoResponse response = memberRepository.getMyInfoResponse(memberId);
		return response;
	}

	public List<MyPageDto.QuizRate> getQuizRate(String nickname) {
		Integer memberId = nickname != null ? memberUtil.getMemberByNickname(nickname).getId() : memberUtil.getLoginMemberId();
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
						.date(score.getDate())
						.averageScore(score.getAverageScore())
						.scoreCount(score.getScoreCount())
						.build());
			}
			result.add(data);
		}
		return result;
	}

	public List<BadgeDto.BadgeResponse> getBadges(String nickname) {
		Integer memberId = nickname != null ? memberUtil.getMemberByNickname(nickname).getId() : memberUtil.getLoginMemberId();
		return rankerRepository.getBadgeListByMemberId(memberId);
	}

	public List<StreakDto.Response> getStreak() {
		Member member = memberUtil.getLoginMember();
		LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);
		List<Streak> streaks = streakRepository.findByMemberIdAndCreatedAtAfterOrderByCreatedAt(member.getId(), oneYearAgo);
		return streaks.stream().map(streakMapper::StreakToResponseDto).toList();
	}

	public List<IncorrectNoteDto.QuizIdPerCategory> getIncorrectNotes() {
		Member member = memberUtil.getLoginMember();
		return myPageRepository.findAllIncorrectQuizByMemberId(member.getId());
	}

	public MyPageDto.IncorrectNoteInfo getIncorrectNoteInfo(Integer quizId){
		return myPageRepository.getAnswersWithQuizId(quizId);
	}
}
