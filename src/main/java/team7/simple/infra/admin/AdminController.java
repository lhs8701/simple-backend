package team7.simple.infra.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team7.simple.domain.answer.dto.AnswerUpdateParam;
import team7.simple.domain.answer.service.AnswerAdminService;
import team7.simple.domain.course.dto.CourseUpdateParam;
import team7.simple.domain.course.service.CourseAdminService;
import team7.simple.domain.question.dto.QuestionUpdateParam;
import team7.simple.domain.question.service.QuestionAdminService;
import team7.simple.domain.unit.dto.UnitUpdateParam;
import team7.simple.domain.unit.service.UnitAdminService;

import javax.validation.Valid;

@Api(tags = {"[Administrator]"})
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {


    private final CourseAdminService courseAdminService;
    private final AnswerAdminService answerAdminService;

    private final QuestionAdminService questionAdminService;

    private final UnitAdminService unitAdminService;

    @ApiOperation(value = "OPEN - 강좌 정보 수정",
            notes = """
                    강좌의 세부 정보를 수정합니다.
                    \nparameter : 수정할 강좌의 아이디
                    \nresponse : 수정된 강좌의 아이디
                    """
    )
    @PatchMapping(value = "/courses/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable Long courseId, @RequestBody @Valid CourseUpdateParam courseUpdateParam) {
        return new ResponseEntity<>(courseAdminService.updateCourse(courseId, courseUpdateParam), HttpStatus.OK);
    }


    @ApiOperation(value = "OPEN - 강좌 삭제",
            notes = """
                    강좌를 삭제합니다.
                    \nparameter : 삭제할 강좌의 아이디
                    \nresponse : X
                    """
    )
    @DeleteMapping(value = "/courses/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId) {
        courseAdminService.deleteCourse(courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 강의 수정",
            notes = """
                    강의 정보를 수정합니다.
                    \nparameter : 수정할 강의 아이디
                    \nresponse : 수정된 강의의 아이디
                    """
    )
    @PatchMapping("/units/{unitId}")
    public ResponseEntity<?> updateUnitDetail(@PathVariable Long unitId, @RequestBody @Valid UnitUpdateParam unitUpdateParam) {
        return new ResponseEntity<>(unitAdminService.updateUnit(unitId, unitUpdateParam), HttpStatus.OK);
    }


    @ApiOperation(value = "OPEN - 강의 삭제",
            notes = """
                    강의를 삭제합니다.
                    \nparameter : 삭제할 강의 아이디
                    \nresponse : X
                    """
    )
    @DeleteMapping("/units/{unitId}")
    public ResponseEntity<?> deleteUnit(@PathVariable Long unitId) {
        unitAdminService.deleteUnit(unitId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "FRONT - 질문 수정",
            notes = """
                    질문을 수정합니다.
                    \nparameter : 질문 아이디
                    \nresponse : 수정한 질문의 아이디
                    """
    )
    @PatchMapping("/questions/{questionId}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long questionId, @RequestBody @Valid QuestionUpdateParam questionUpdateParam) {
        return new ResponseEntity<>(questionAdminService.updateQuestion(questionId, questionUpdateParam), HttpStatus.OK);
    }


    @ApiOperation(value = "FRONT - 질문 삭제",
            notes = """
                    질문을 삭제합니다.
                    \nparameter : 질문 아이디
                    \nresponse : X
                    """
    )
    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
        questionAdminService.deleteQuestion(questionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "FRONT - 답변 수정",
            notes = """
                    답변을 수정합니다.
                    \nparameter : 답변 아이디
                    \nresponse : 수정된 답변의 아이디
                    """
    )
    @PatchMapping("/front/answers/{answerId}")
    public ResponseEntity<?> updateAnswer(@PathVariable Long answerId, @RequestBody @Valid AnswerUpdateParam answerUpdateParam) {
        return new ResponseEntity<>(answerAdminService.updateAnswer(answerId, answerUpdateParam), HttpStatus.OK);
    }


    @ApiOperation(value = "FRONT - 답변 삭제",
            notes = """
                    답변을 삭제합니다.
                    \nparameter : 답변 아이디
                    \nresponse : X
                    """
    )
    @DeleteMapping("/front/answers/{answerId}")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long answerId) {
        answerAdminService.deleteAnswer(answerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
