package team7.simple.domain.question.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team7.simple.domain.question.dto.QuestionRequestDto;
import team7.simple.domain.question.dto.QuestionUpdateParam;
import team7.simple.domain.question.service.QuestionService;

import javax.validation.Valid;

@Api(tags = {"Question Controller"})
@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @ApiOperation(value = "FRONT - 질문 등록")
    @PostMapping("/front/course/unit/{unitId}/question")
    public ResponseEntity<?> uploadQuestion(@PathVariable Long unitId, @Valid @RequestBody QuestionRequestDto questionRequestDto) {
        return new ResponseEntity<>(questionService.createQuestion(unitId, questionRequestDto), HttpStatus.OK);
    }
    @ApiOperation(value = "FRONT - 질문 수정")
    @PatchMapping("/front/course/unit/question/{questionId}")
    public ResponseEntity<?> updateQuestion(@RequestBody @Valid QuestionUpdateParam questionUpdateParam) {
        return new ResponseEntity<>(questionService.updateQuestion(questionUpdateParam), HttpStatus.OK);
    }

    @ApiOperation(value = "FRONT - 질문 삭제")
    @DeleteMapping("/front/course/unit/question/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "FRONT - 강의 질문 목록 조회")
    @DeleteMapping("/front/course/unit/{unitId}/question/")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "FRONT - 질문 상세 조회")
    @GetMapping("/front/course/unit/question/{questionId}")
    public ResponseEntity<?> getQuestionInfo(@PathVariable Long questionId) {
        return new ResponseEntity<>(questionService.getQuestionInfo(questionId), HttpStatus.OK);
    }

}
