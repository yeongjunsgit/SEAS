package com.ssafy.seas.ranking.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.seas.common.constants.ErrorCode;
import com.ssafy.seas.common.constants.SuccessCode;
import com.ssafy.seas.common.dto.ApiResponse;
import com.ssafy.seas.ranking.dto.BadgeDto;
import com.ssafy.seas.ranking.dto.RankDto;
import com.ssafy.seas.ranking.dto.RankerDto;
import com.ssafy.seas.ranking.service.RankingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
public class RankingController {
	private final RankingService rankingService;

	@GetMapping("/list")
	public ApiResponse<RankDto.Response> getRankingList() {
		try {
			// Todo : 현재 로그인한 유저의 id 가져오기
			String uuid = "toast";
			List<RankerDto.RankResponse> rankerDtoList = rankingService.getRankers();
			List<RankerDto.RankResponse> rankerDtoTop3List = new ArrayList<>();
			List<RankerDto.RankResponse> myRankDto = rankingService.getMyRank(uuid);

			for(RankerDto.RankResponse currentRanker : rankerDtoList){
				List<BadgeDto.BadgeResponse> badgeList = rankingService.getBadgeList(currentRanker.getNickname());
				currentRanker.setBadgeList(badgeList);
			}

			for(int i = 0; i < 3 && i < rankerDtoList.size(); i++){
				rankerDtoTop3List.add(rankerDtoList.get(i));
			}

			if(myRankDto.size() == 1){
				RankerDto.RankResponse myDto = myRankDto.get(0);
				myDto.setBadgeList(rankingService.getBadgeList(myDto.getNickname()));
			}

			return ApiResponse.success(SuccessCode.GET_SUCCESS, new RankDto.Response(rankerDtoTop3List, rankerDtoList, myRankDto.get(0)));
		} catch (Exception e) {
			e.printStackTrace();
			return ApiResponse.error(ErrorCode.SERVER_ERROR);
		}
	}

	@GetMapping("/search")
	public ApiResponse<List<RankerDto.RankResponseWithRanking>> getMemberRanking(@RequestParam("search") String searchNickname) {
		try {
			// list로 넘겨달라는 요청이 있었음.
			List<RankerDto.RankResponseWithRanking> result = rankingService.getRankByNickname(searchNickname);
			return ApiResponse.success(SuccessCode.GET_SUCCESS, result);
		} catch (Exception e) {
			e.printStackTrace();
			return ApiResponse.error(ErrorCode.SERVER_ERROR);
		}
	}
}
