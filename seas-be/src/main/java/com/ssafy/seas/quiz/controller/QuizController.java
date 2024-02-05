package com.ssafy.seas.quiz.controller;

import com.ssafy.seas.common.constants.SuccessCode;
import com.ssafy.seas.common.dto.ApiResponse;
import com.ssafy.seas.quiz.dto.QuizHintDto;
import com.ssafy.seas.quiz.dto.QuizListDto;
import com.ssafy.seas.quiz.service.QuizService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService){
        this.quizService = quizService;
    }

    // 퀴즈 목록 불러오기
    @GetMapping("")
    public ApiResponse<List<QuizListDto.QuizInfo>> getQuizzezList(QuizListDto.Request request){
        return ApiResponse.success(SuccessCode.GET_SUCCESS, quizService.getQuizzes(request));
    }


    @GetMapping("/{quizId}/hint")
    public ApiResponse<QuizHintDto.Response> getHint(@PathVariable("quizId") Integer quizId, @RequestParam("id") Integer memberId){


        return ApiResponse.success(SuccessCode.GET_SUCCESS, quizService.getHint(quizId, memberId));
    }




}
