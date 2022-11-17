package team7.simple.domain.answer.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team7.simple.domain.answer.dto.AnswerRequestDto;
import team7.simple.domain.answer.dto.AnswerUpdateParam;
import team7.simple.domain.answer.service.AnswerService;

import javax.validation.Valid;
@Api(tags = {"Answer Controller"})
@RestController
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;
    @ApiOperation(value = "FRONT - 답변 등록")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "답변에 대한 질문을 찾을 수 없을 경우"),
    })
    @PostMapping("/front/course/unit/question/{questionId}/answer")
    public ResponseEntity<?> uploadAnswer(@PathVariable Long questionId, @Valid @RequestBody AnswerRequestDto answerRequestDto) {
        return new ResponseEntity<>(answerService.createAnswer(questionId, answerRequestDto), HttpStatus.OK);
    }

    @ApiOperation(value = "FRONT - 답변 수정")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 답변을 찾을 수 없을 경우"),
    })
    @PatchMapping("/front/course/unit/question/answer/{answerId}")
    public ResponseEntity<?> updateAnswer(@RequestBody @Valid AnswerUpdateParam answerUpdateParam) {
        return new ResponseEntity<>(answerService.updateAnswer(answerUpdateParam), HttpStatus.OK);
    }

    @ApiOperation(value = "FRONT - 답변 삭제")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 답변을 찾을 수 없을 경우"),
    })
    @DeleteMapping("/front/course/unit/question/answer/{answerId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long answerId) {
        answerService.deleteAnswer(answerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "FRONT - 답변 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 답변을 찾을 수 없을 경우"),
    })
    @GetMapping("/front/course/unit/question/answer/{answerId}")
    public ResponseEntity<?> getAnswerInfo(@PathVariable Long answerId) {
        return new ResponseEntity<>(answerService.getAnswerInfo(answerId), HttpStatus.OK);
    }
}
