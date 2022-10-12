package team7.simple.domain.unit;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team7.simple.domain.unit.dto.UnitRequestDto;
import team7.simple.domain.unit.dto.UnitUpdateParam;
import team7.simple.domain.unit.service.UnitService;
import team7.simple.global.common.response.dto.CommonResult;
import team7.simple.global.common.response.dto.SingleResult;
import team7.simple.global.common.response.service.ResponseService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UnitController {
    private final UnitService unitService;
    private final ResponseService responseService;

//    @PostMapping("open/course/unit")
//    public SingleResult<Long> uploadUnit(@RequestPart @Valid UnitRequestDto unitRequestDto, @RequestPart MultipartFile file) {
//        return responseService.getSingleResult(unitService.createUnit(unitRequestDto, file));
//    }

    /*임시*/
    @PostMapping("open/course/unit")
    public SingleResult<Long> uploadUnit(@RequestBody @Valid UnitRequestDto unitRequestDto) {
        return responseService.getSingleResult(unitService.createUnit(unitRequestDto));
    }


    @PatchMapping("/open/course/unit/{unitId}")
    public SingleResult<Long> updateUnit(@RequestBody @Valid UnitUpdateParam unitUpdateParam) {
        return responseService.getSingleResult(unitService.updateUnit(unitUpdateParam));
    }

    @DeleteMapping("/open/course/unit/{unitId}")
    public CommonResult deleteUnit(@PathVariable Long unitId) {
        unitService.deleteUnit(unitId);
        return responseService.getSuccessResult();
    }
}
