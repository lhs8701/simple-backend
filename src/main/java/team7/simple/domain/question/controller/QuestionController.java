package team7.simple.domain.question.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team7.simple.domain.question.dto.QuestionRequestDto;
import team7.simple.domain.question.dto.QuestionUpdateParam;
import team7.simple.domain.question.service.QuestionService;

import javax.validation.Valid;

@Api(tags = {"[Front API] Question"})
@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @ApiOperation(value = "FRONT - 질문 등록")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "해당 강의가 없을 경우"),
    })
    @PostMapping("/front/unit/{unitId}/questions")
    public ResponseEntity<?> uploadQuestion(Long unitId, @Valid @RequestBody QuestionRequestDto questionRequestDto) {
        return new ResponseEntity<>(questionService.createQuestion(unitId, questionRequestDto), HttpStatus.OK);
    }

    @ApiOperation(value = "FRONT - 강의 질문 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "해당 강의가 없을 경우"),
    })
    @RequestMapping(value = "/front/unit/{unitId}/questions", method = RequestMethod.GET)
    public ResponseEntity<?> getQuestions(Long unitId) {
        return new ResponseEntity<>(questionService.getQuestionList(unitId), HttpStatus.OK);
    }

    @ApiOperation(value = "FRONT - 질문 수정")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "해당 질문이 없을 경우"),
    })
    @PatchMapping("/front/questions/{questionId}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long questionId, @RequestBody @Valid QuestionUpdateParam questionUpdateParam) {
        return new ResponseEntity<>(questionService.updateQuestion(questionId, questionUpdateParam), HttpStatus.OK);
    }

    @ApiOperation(value = "FRONT - 질문 삭제")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "해당 질문이 없을 경우"),
    })
    @DeleteMapping("/front/questions/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestionById(questionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
