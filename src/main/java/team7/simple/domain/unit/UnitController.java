package team7.simple.domain.unit;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team7.simple.domain.unit.dto.UnitRequestDto;
import team7.simple.domain.unit.dto.UnitResponseDto;
import team7.simple.domain.unit.dto.UnitUpdateParam;
import team7.simple.domain.unit.service.UnitService;
import team7.simple.global.common.response.service.ResponseService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UnitController {
    private final UnitService unitService;

    @ApiOperation(value = "OPEN - 강의 업로드")
    @PostMapping("open/course/unit")
    public ResponseEntity<?> uploadUnit(@RequestPart @Valid UnitRequestDto unitRequestDto, @RequestPart MultipartFile file) {
        return new ResponseEntity<>(unitService.createUnit(unitRequestDto, file), HttpStatus.OK);
    }

    /*테스트용*/
    @ApiOperation(value = "OPEN - 강의 업로드 (로컬 테스트용)")
    @PostMapping("open/course/unit/local")
    public ResponseEntity<?> uploadUnitLocal(@RequestBody @Valid UnitRequestDto unitRequestDto) {
        return new ResponseEntity<>(unitService.createUnitLocal(unitRequestDto), HttpStatus.OK);
    }


    @ApiOperation(value = "OPEN - 강의 수정")
    @PatchMapping("/open/course/unit/{unitId}")
    public ResponseEntity<?> updateUnit(@RequestBody @Valid UnitUpdateParam unitUpdateParam) {
        return new ResponseEntity<>(unitService.updateUnit(unitUpdateParam), HttpStatus.OK);
    }

    @ApiOperation(value = "OPEN - 강의 삭제")
    @DeleteMapping("/open/course/unit/{unitId}")
    public ResponseEntity<?> deleteUnit(@PathVariable Long unitId) {
        unitService.deleteUnit(unitId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "FRONT - 강의 정보 조회")
    @GetMapping("/front/course/unit/{unitId}")
    public ResponseEntity<?> getUnitInfo(@PathVariable Long unitId) {
        return new ResponseEntity<>(unitService.getUnitInfo(unitId), HttpStatus.OK);
    }
}
