package com.ssafy.seas.quiz.controller;

import com.ssafy.seas.common.constants.ErrorCode;
import com.ssafy.seas.common.constants.SuccessCode;
import com.ssafy.seas.common.dto.ApiResponse;
import com.ssafy.seas.quiz.dto.QuizAnswerDto;
import com.ssafy.seas.quiz.dto.QuizHintDto;
import com.ssafy.seas.quiz.dto.QuizListDto;
import com.ssafy.seas.quiz.dto.QuizResultDto;
import com.ssafy.seas.quiz.service.QuizService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerErrorException;

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


    @GetMapping("/hint/{categoryId}/{quizId}")
    public ApiResponse<QuizHintDto.Response> getHint(
            @PathVariable("categoryId") Integer categoryId,
            @PathVariable("quizId") Integer quizId){

        return ApiResponse.success(SuccessCode.GET_SUCCESS, quizService.getHint(quizId));
    }

    @PostMapping("/answer/{categoryId}/{quizId}")
    public ApiResponse<QuizAnswerDto.Response> getSingleAnswerPerQuiz(@RequestBody QuizAnswerDto.Request request,
                                                                      @PathVariable("categoryId") Integer categoryId,
                                                                      @PathVariable("quizId") Integer quizId) {
        try {
            return ApiResponse.success(SuccessCode.GET_SUCCESS, quizService.getSubmitResult(request, categoryId, quizId));
        }
        catch (IllegalStateException e){
            return ApiResponse.error(ErrorCode.BAD_PARAMETER);
        }
        catch (ServerErrorException e){
            return ApiResponse.error(ErrorCode.SERVER_ERROR);
        }
    }

    @GetMapping("/result")
    public ApiResponse<QuizResultDto.Response> getTotalResult(){
        return ApiResponse.success(SuccessCode.GET_SUCCESS, quizService.getTotalResult());
    }

    @GetMapping("/tier")
    public ApiResponse<?> getTier(){
        return ApiResponse.success(SuccessCode.GET_SUCCESS, quizService.getTier());
    }

//    @GetMapping("/tier")
//    public ApiResponse<> getTier(){
//
//
//    }
}