package team7.simple.openapi;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team7.simple.domain.unit.dto.UnitRequestByUrlDto;
import team7.simple.domain.unit.dto.UnitRequestDto;
import team7.simple.domain.unit.dto.UnitUpdateParam;
import team7.simple.domain.unit.service.UnitService;
import team7.simple.global.common.ConstValue;

import javax.validation.Valid;

@Api(tags = {"[Open API] Unit"})
@Controller
@RequiredArgsConstructor
public class UnitOpenController {
    private final UnitService unitService;

    @ApiOperation(value = "OPEN - 강의 업로드", notes = "강의 원본 영상을 원격 서버에 업로드함과 동시에 m3u8 확장자로 변환합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "해당 강좌가 없을 경우"),
    })
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/open/courses/{courseId}/units")
    public ResponseEntity<?> uploadUnit(@PathVariable Long courseId, @RequestPart @Valid UnitRequestDto unitRequestDto, @RequestPart MultipartFile file) {
        return new ResponseEntity<>(unitService.createUnit(courseId, unitRequestDto, file), HttpStatus.OK);
    }

    /*테스트용*/
    @ApiOperation(value = "TEST - 강의 업로드 (로컬 테스트용)", notes = "로컬 환경에서 테스트 용으로 사용하는 API입니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "해당 강좌가 없을 경우"),
    })
    @PreAuthorize("permitAll()")
    @PostMapping("open/course/{courseId}/units/test-local")
    public ResponseEntity<?> uploadUnitLocal(@PathVariable Long courseId, @RequestBody UnitRequestDto unitRequestDto) {
        return new ResponseEntity<>(unitService.createUnitLocal(courseId, unitRequestDto), HttpStatus.OK);
    }

    /*테스트용*/
    @ApiOperation(value = "TEST - 강의 업로드 (url로 접근, 테스트용) ", notes = "URL로 미디어 소스에 접근합니다. 테스트 용으로 사용하는 API입니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "해당 강좌가 없을 경우"),
    })
    @PreAuthorize("permitAll()")
    @PostMapping("open/course/{courseId}/units/test-url")
    public ResponseEntity<?> uploadUnitByUrl(@PathVariable Long courseId, @RequestBody UnitRequestByUrlDto unitRequestByUrlDto) {
        return new ResponseEntity<>(unitService.createUnitByUrl(courseId, unitRequestByUrlDto), HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 강좌 내 강의 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "해당 강좌가 없을 경우"),
    })
    @PreAuthorize("permitAll()")
    @GetMapping("/open/courses/{courseId}/units")
    public ResponseEntity<?> getUnitThumbnailList(@PathVariable Long courseId) {
        return new ResponseEntity<>(unitService.getUnits(courseId), HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 강의 수정")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "해당 강의가 없을 경우"),
    })
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/open/units/{unitId}")
    public ResponseEntity<?> updateUnit(@PathVariable Long unitId, @RequestBody @Valid UnitUpdateParam unitUpdateParam) {
        return new ResponseEntity<>(unitService.updateUnit(unitId, unitUpdateParam), HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 강의 삭제")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "해당 강의가 없을 경우"),
    })
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/open/units/{unitId}")
    public ResponseEntity<?> deleteUnit(@PathVariable Long unitId) {
        unitService.deleteUnit(unitId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
