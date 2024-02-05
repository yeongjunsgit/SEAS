package com.ssafy.seas.quiz.controller;

import com.ssafy.seas.common.constants.SuccessCode;
import com.ssafy.seas.common.dto.ApiResponse;
import com.ssafy.seas.quiz.dto.QuizListDto;
import com.ssafy.seas.quiz.service.QuizService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService){
        this.quizService = quizService;
    }

    // 퀴즈 목록 불러오기
    @GetMapping("")
    public ApiResponse<QuizListDto.Response> getQuizzezList(QuizListDto.Request request){
        return ApiResponse.success(SuccessCode.GET_SUCCESS, quizService.getQuizzes(request));
    }




}
