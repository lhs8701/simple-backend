package team7.simple.domain.unit.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team7.simple.domain.unit.dto.UnitRequestDto;
import team7.simple.domain.unit.dto.UnitUpdateParam;
import team7.simple.domain.unit.service.UnitService;
import team7.simple.domain.user.entity.User;
import team7.simple.global.common.constant.ConstValue;

import javax.validation.Valid;

@Api(tags = {"[Open API] Unit"})
@Controller
@RequiredArgsConstructor
public class UnitOpenController {
    private final UnitService unitService;

    @ApiOperation(value = "OPEN - 강의 업로드",
            notes = """
                    강의 원본 영상을 원격 서버에 업로드함과 동시에 m3u8 확장자로 변환합니다.
                    \nparameter : 강의를 등록할 강좌의 아이디, 강의 제목, 강의 설명, 강의 목표
                    \nresponse : 업로드한 강의 아이디
                    """
    )
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/open/courses/{courseId}/units")
    public ResponseEntity<?> uploadUnit(@PathVariable Long courseId, @RequestPart @Valid UnitRequestDto unitRequestDto, @RequestPart MultipartFile file) {
        return new ResponseEntity<>(unitService.createUnit(courseId, unitRequestDto, file), HttpStatus.OK);
    }


    /*테스트용*/
    @ApiOperation(value = "TEST - 강의 업로드 (로컬 테스트용)", notes = "로컬 환경에서 테스트 용으로 사용하는 API입니다.")
    @PreAuthorize("permitAll()")
    @PostMapping("open/course/{courseId}/units/test-local")
    public ResponseEntity<?> uploadUnitLocal(@PathVariable Long courseId, @RequestBody UnitRequestDto unitRequestDto) {
        return new ResponseEntity<>(unitService.createUnitLocal(courseId, unitRequestDto), HttpStatus.OK);
    }


    @ApiOperation(value = "OPEN - 강좌 목차 조회",
            notes = """
                    강좌의 강의 목차를 조회합니다.
                    \nparameter : 조회할 강의 아이디
                    \nresponse : 강의 목차 정보 (강의 아이디, 강의 제목)
                    """
    )
    @PreAuthorize("permitAll()")
    @GetMapping("/open/courses/{courseId}/units")
    public ResponseEntity<?> displayTableOfContents(@PathVariable Long courseId) {
        return new ResponseEntity<>(unitService.getUnits(courseId), HttpStatus.OK);
    }


    @ApiOperation(value = "OPEN - 강의 세부 정보 조회",
            notes = """
                    강의의 세부 정보를 조회합니다.
                    \nparameter : 조회할 강의 아이디
                    \nresponse : 강의 아이디, 강의 제목, 강의 설명, 강의 목표, 강의 평균 평점, 세부 평점 목록
                    """
    )
    @PreAuthorize("permitAll()")
    @GetMapping("/open/units/{unitId}")
    public ResponseEntity<?> displayUnitDetail(@PathVariable Long unitId) {
        return new ResponseEntity<>(unitService.getUnitDetail(unitId), HttpStatus.OK);
    }


    @ApiOperation(value = "OPEN - 강의 수정",
            notes = """
                    강의 정보를 수정합니다.
                    \nparameter : 수정할 강의 아이디
                    \nresponse : 수정된 강의의 아이디
                    """
    )
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/open/units/{unitId}")
    public ResponseEntity<?> updateUnitDetail(@PathVariable Long unitId, @RequestBody @Valid UnitUpdateParam unitUpdateParam, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(unitService.updateUnit(unitId, unitUpdateParam, user), HttpStatus.OK);
    }


    @ApiOperation(value = "OPEN - 강의 삭제",
            notes = """
                    강의를 삭제합니다.
                    \nparameter : 삭제할 강의 아이디
                    \nresponse : X
                    """
    )
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/open/units/{unitId}")
    public ResponseEntity<?> deleteUnit(@PathVariable Long unitId, @AuthenticationPrincipal User user) {
        unitService.deleteUnit(unitId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
