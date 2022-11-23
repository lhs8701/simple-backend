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
import springfox.documentation.annotations.ApiIgnore;
import team7.simple.domain.unit.dto.UnitRequestByUrlDto;
import team7.simple.domain.unit.dto.UnitRequestDto;
import team7.simple.domain.unit.dto.UnitUpdateParam;
import team7.simple.domain.unit.service.UnitService;

import javax.validation.Valid;

@Api(tags = {"[Open API] Unit"})
@Controller
@RequiredArgsConstructor
@RequestMapping("/open/course")
public class UnitOpenController {
    private final UnitService unitService;

    @ApiOperation(value = "OPEN - 강좌 내 강의 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 강좌가 없을 경우"),
    })
    @GetMapping("/{courseId}/unit")
    public ResponseEntity<?> getUnitThumbnailList(@PathVariable Long courseId) {
        return new ResponseEntity<>(unitService.getUnitThumbnailList(courseId), HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 강의 업로드", notes = "강의 원본 영상을 원격 서버에 업로드함과 동시에 m3u8 확장자로 변환합니다.")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 강좌가 없을 경우"),
    })
    @PostMapping("/unit")
    public ResponseEntity<?> uploadUnit(@RequestPart @Valid UnitRequestDto unitRequestDto, @RequestPart MultipartFile file) {
        return new ResponseEntity<>(unitService.createUnit(unitRequestDto, file), HttpStatus.OK);
    }

    /*테스트용*/
    @ApiIgnore
    @ApiOperation(value = "OPEN - 강의 업로드 (로컬 테스트용)", notes = "로컬 환경에서 테스트 용으로 사용하는 API입니다.")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 강좌가 없을 경우"),
    })
    @PostMapping("/unit/local")
    public ResponseEntity<?> uploadUnitLocal(@RequestBody @Valid UnitRequestDto unitRequestDto) {
        return new ResponseEntity<>(unitService.createUnitLocal(unitRequestDto), HttpStatus.OK);
    }

    /*테스트용*/
    @ApiIgnore
    @ApiOperation(value = "OPEN - 강의 업로드 (url로 접근, 테스트용) ", notes = "URL로 미디어 소스에 접근합니다. 테스트 용으로 사용하는 API입니다.")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 강좌가 없을 경우"),
    })
    @PostMapping("/unit/url")
    public ResponseEntity<?> uploadUnitByUrl(@RequestBody @Valid UnitRequestByUrlDto unitRequestByUrlDto) {
        return new ResponseEntity<>(unitService.createUnitByUrl(unitRequestByUrlDto), HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 강의 수정")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 강의가 없을 경우"),
    })
    @PatchMapping("/unit/{unitId}")
    public ResponseEntity<?> updateUnit(@RequestBody @Valid UnitUpdateParam unitUpdateParam) {
        return new ResponseEntity<>(unitService.updateUnit(unitUpdateParam), HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 강의 삭제")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 강의가 없을 경우"),
    })
    @DeleteMapping("/unit/{unitId}")
    public ResponseEntity<?> deleteUnit(@PathVariable Long unitId) {
        unitService.deleteUnit(unitId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
