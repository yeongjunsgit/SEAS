package com.ssafy.seas.quiz.controller;

import com.ssafy.seas.common.constants.ErrorCode;
import com.ssafy.seas.common.constants.SuccessCode;
import com.ssafy.seas.common.dto.ApiResponse;
import com.ssafy.seas.quiz.dto.QuizAnswerDto;
import com.ssafy.seas.quiz.dto.QuizHintDto;
import com.ssafy.seas.quiz.dto.QuizListDto;
import com.ssafy.seas.quiz.service.QuizService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService){
        this.quizService = quizService;
    }

    // 퀴즈 목록 불러오기

    @GetMapping("/{categoryId}")
    public ApiResponse<QuizListDto.Response> getQuizzezList(@PathVariable("categoryId") int categoryId){

        return ApiResponse.success(SuccessCode.GET_SUCCESS, quizService.getQuizzes(categoryId));
    }


    @GetMapping("/{quizId}/hint")
    public ApiResponse<QuizHintDto.Response> getHint(@PathVariable("quizId") Integer quizId){

        return ApiResponse.success(SuccessCode.GET_SUCCESS, quizService.getHint(quizId));
    }

    @PostMapping("/{quizId}/answer")
    public ApiResponse<QuizAnswerDto.Response> getResult(@RequestBody QuizAnswerDto.Request request, Integer quizId){
        try {
            return ApiResponse.success(SuccessCode.GET_SUCCESS, quizService.getSubmitResult(request, quizId));
        }
        catch (IllegalStateException e){
            return ApiResponse.error(ErrorCode.BAD_PARAMETER);
        }
    }

}
