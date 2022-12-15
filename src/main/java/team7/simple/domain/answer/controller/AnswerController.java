package team7.simple.domain.answer.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team7.simple.domain.answer.dto.AnswerRequestDto;
import team7.simple.domain.answer.dto.AnswerUpdateParam;
import team7.simple.domain.answer.service.AnswerService;
import team7.simple.domain.user.entity.User;
import team7.simple.global.common.constant.ConstValue;

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
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/front/questions/{questionId}/answers")
    public ResponseEntity<?> uploadAnswer(@PathVariable Long questionId, @Valid @RequestBody AnswerRequestDto answerRequestDto, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(answerService.createAnswer(questionId, answerRequestDto, user), HttpStatus.OK);
    }


    @ApiOperation(value = "FRONT - 답변 전체 조회",
            notes = """
                    질문에 달린 모든 답변을 조회합니다.
                    \nparameter : 질문 아이디
                    \nresponse : 답변 아이디, 답변 내용, 답변 등록 일자, 답변 수정 일자
                    """
    )
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
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/front/answers/{answerId}")
    public ResponseEntity<?> updateAnswer(@PathVariable Long answerId, @RequestBody @Valid AnswerUpdateParam answerUpdateParam, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(answerService.updateAnswer(answerId, answerUpdateParam, user), HttpStatus.OK);
    }


    @ApiOperation(value = "FRONT - 답변 삭제",
            notes = """
                    답변을 삭제합니다.
                    \nparameter : 답변 아이디
                    \nresponse : X
                    """
    )
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/front/answers/{answerId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long answerId, @AuthenticationPrincipal User user) {
        answerService.deleteAnswer(answerId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
