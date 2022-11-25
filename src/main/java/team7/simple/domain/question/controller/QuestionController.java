package team7.simple.domain.question.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team7.simple.domain.question.dto.QuestionRequestDto;
import team7.simple.domain.question.dto.QuestionUpdateParam;
import team7.simple.domain.question.service.QuestionService;
import team7.simple.domain.user.entity.User;
import team7.simple.global.common.constant.ConstValue;

import javax.validation.Valid;

@Api(tags = {"[Front API] Question"})
@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @ApiOperation(value = "FRONT - 질문 등록",
            notes = """
                    강의의 질문을 등록합니다.
                    \nparameter : 강의 아이디, 질문 제목, 질문 내용, 질문이 발생한 타임라인
                    \nresponse : 등록된 질문의 아이디
                    """
    )
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/front/unit/{unitId}/questions")
    public ResponseEntity<?> uploadQuestion(@PathVariable Long unitId, @Valid @RequestBody QuestionRequestDto questionRequestDto, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(questionService.createQuestion(unitId, questionRequestDto, user), HttpStatus.OK);
    }


    @ApiOperation(value = "FRONT - 강의 질문 목록 조회",
            notes = """
                    강의의 질문 목록을 반환합니다.
                    \nparameter : 강의 아이디
                    \nresponse : 질문 아이디, 질문 제목, 답변 수
                    """
    )
    @GetMapping(value = "/front/unit/{unitId}/questions")
    public ResponseEntity<?> getQuestions(@PathVariable Long unitId) {
        return new ResponseEntity<>(questionService.getQuestionList(unitId), HttpStatus.OK);
    }


    @ApiOperation(
            value = "FRONT - 강의 질문 세부 조회",
            notes = """
                    질문의 세부 정보를 반환합니다.
                    \nparameter : 질문 아이디
                    \nresponse : 강의 아이디, 질문 제목, 질문 내용, 답변 수, 질문 시간대, 질문 생성 시간
                    """
    )
    @GetMapping(value = "/front/questions/{questionId}")
    public ResponseEntity<?> getQuestionDetail(@PathVariable Long questionId) {
        return new ResponseEntity<>(questionService.getQuestionDetail(questionId), HttpStatus.OK);
    }


    @ApiOperation(value = "FRONT - 질문 수정",
            notes = """
                    질문을 수정합니다.
                    \nparameter : 질문 아이디
                    \nresponse : 수정한 질문의 아이디
                    """
    )
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/front/questions/{questionId}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long questionId, @RequestBody @Valid QuestionUpdateParam questionUpdateParam, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(questionService.updateQuestion(questionId, questionUpdateParam, user), HttpStatus.OK);
    }


    @ApiOperation(value = "FRONT - 질문 삭제",
            notes = """
                    질문을 삭제합니다.
                    \nparameter : 질문 아이디
                    \nresponse : X
                    """
    )
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/front/questions/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId, @AuthenticationPrincipal User user) {
        questionService.deleteQuestion(questionId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
