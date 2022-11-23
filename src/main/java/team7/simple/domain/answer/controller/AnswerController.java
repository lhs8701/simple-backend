package team7.simple.domain.answer.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team7.simple.domain.answer.dto.AnswerRequestDto;
import team7.simple.domain.answer.dto.AnswerUpdateParam;
import team7.simple.domain.answer.service.AnswerService;

import javax.validation.Valid;
@Api(tags = {"[Front API] Answer"})
@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;
    @ApiOperation(value = "FRONT - 답변 등록")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "답변에 대한 질문을 찾을 수 없을 경우"),
    })
    @PostMapping("/front/questions/{questionId}/answers")
    public ResponseEntity<?> uploadAnswer(@PathVariable Long questionId, @Valid @RequestBody AnswerRequestDto answerRequestDto) {
        return new ResponseEntity<>(answerService.createAnswer(questionId, answerRequestDto), HttpStatus.OK);
    }

    /* 질문에 대한 답변 전체 조회 추가 예정 */
//    @ApiOperation(value = "FRONT - 답변 전체 조회")
//    @ApiResponses(value = {
//            @ApiResponse(code=200, message = "성공"),
//            @ApiResponse(code=404, message = "해당 답변을 찾을 수 없을 경우"),
//    })
//    @GetMapping("/front/questions/{questionId}/answers")
//    public ResponseEntity<?> getAnswers(@PathVariable Long questionId) {
//        return new ResponseEntity<>(answerService.getAnswers(questionId), HttpStatus.OK);
//    }

    /* 필요성에 대해 다시 한번 생각해보기 */
    @ApiOperation(value = "FRONT - 답변 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 답변을 찾을 수 없을 경우"),
    })
    @GetMapping("/front/answers/{answerId}")
    public ResponseEntity<?> getAnswerDetail(@PathVariable Long answerId) {
        return new ResponseEntity<>(answerService.getAnswerInfo(answerId), HttpStatus.OK);
    }

    @ApiOperation(value = "FRONT - 답변 수정")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 답변을 찾을 수 없을 경우"),
    })
    @PatchMapping("/front/answers/{answerId}")
    public ResponseEntity<?> updateAnswer(@RequestBody @Valid AnswerUpdateParam answerUpdateParam) {
        return new ResponseEntity<>(answerService.updateAnswer(answerUpdateParam), HttpStatus.OK);
    }

    @ApiOperation(value = "FRONT - 답변 삭제")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 답변을 찾을 수 없을 경우"),
    })
    @DeleteMapping("/front/answers/{answerId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long answerId) {
        answerService.deleteAnswer(answerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
