package team7.simple.openapi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team7.simple.domain.unit.dto.UnitRequestDto;
import team7.simple.domain.unit.dto.UnitUpdateParam;
import team7.simple.domain.unit.service.UnitService;

import javax.validation.Valid;

@Api(tags = {"[Open API] Unit"})
@Controller
@RequiredArgsConstructor
public class UnitOpenController {
    private final UnitService unitService;

    @ApiOperation(value = "OPEN - 강의 업로드", notes = "강의 원본 영상을 원격 서버에 업로드함과 동시에 m3u8 확장자로 변환합니다.")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 강좌가 없을 경우"),
    })
    @PostMapping("/open/courses/{courseId}/units")
    public ResponseEntity<?> uploadUnit(@PathVariable Long courseId, @RequestPart @Valid UnitRequestDto unitRequestDto, @RequestPart MultipartFile file) {
        return new ResponseEntity<>(unitService.createUnit(courseId, unitRequestDto, file), HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 강좌 내 강의 전체 조회")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 강좌가 없을 경우"),
    })
    @GetMapping("/open/courses/{courseId}/units")
    public ResponseEntity<?> getUnitThumbnailList(@PathVariable Long courseId) {
        return new ResponseEntity<>(unitService.getUnits(courseId), HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 강의 수정")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 강의가 없을 경우"),
    })
    @PatchMapping("/open/units/{unitId}")
    public ResponseEntity<?> updateUnit(@PathVariable Long unitId, @RequestBody @Valid UnitUpdateParam unitUpdateParam) {
        return new ResponseEntity<>(unitService.updateUnit(unitId, unitUpdateParam), HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 강의 삭제")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 강의가 없을 경우"),
    })
    @DeleteMapping("/open/units/{unitId}")
    public ResponseEntity<?> deleteUnit(@PathVariable Long unitId) {
        unitService.deleteUnit(unitId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
