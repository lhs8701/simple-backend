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

    @ApiOperation(value = "FRONT - 답변 등록",
            notes = """
                    질문에 대한 답변을 등록합니다.
                    \nparameter : 질문 아이디, 답변 내용
                    \nresponse : 등록한 답변의 아이디
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "답변에 대한 질문을 찾을 수 없을 경우"),
    })
    @PostMapping("/front/questions/{questionId}/answers")
    public ResponseEntity<?> uploadAnswer(@PathVariable Long questionId, @Valid @RequestBody AnswerRequestDto answerRequestDto) {
        return new ResponseEntity<>(answerService.createAnswer(questionId, answerRequestDto), HttpStatus.OK);
    }


    @ApiOperation(value = "FRONT - 답변 전체 조회",
            notes = """
                    질문에 달린 모든 답변을 조회합니다.
                    \nparameter : 질문 아이디
                    \nresponse : 답변 아이디, 답변 내용, 답변 등록 일자, 답변 수정 일자
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "해당 답변을 찾을 수 없을 경우"),
    })
    @GetMapping("/front/questions/{questionId}/answers")
    public ResponseEntity<?> getAnswers(@PathVariable Long questionId) {
        return new ResponseEntity<>(answerService.getAnswerList(questionId), HttpStatus.OK);
    }


    @ApiOperation(value = "FRONT - 답변 수정",
            notes = """
                    답변을 수정합니다.
                    \nparameter : 답변 아이디
                    \nresponse : 수정된 답변의 아이디
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "해당 답변을 찾을 수 없을 경우"),
    })
    @PatchMapping("/front/answers/{answerId}")
    public ResponseEntity<?> updateAnswer(@RequestBody @Valid AnswerUpdateParam answerUpdateParam) {
        return new ResponseEntity<>(answerService.updateAnswer(answerUpdateParam), HttpStatus.OK);
    }


    @ApiOperation(value = "FRONT - 답변 삭제",
            notes = """
                    답변을 삭제합니다.
                    \nparameter : 답변 아이디
                    \nresponse : X
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "해당 답변을 찾을 수 없을 경우"),
    })
    @DeleteMapping("/front/answers/{answerId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long answerId) {
        answerService.deleteAnswer(answerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
