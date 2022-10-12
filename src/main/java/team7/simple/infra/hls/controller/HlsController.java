package team7.simple.infra.hls.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team7.simple.global.common.response.dto.CommonResult;
import team7.simple.global.common.response.dto.SingleResult;
import team7.simple.global.common.response.service.ResponseService;
import team7.simple.infra.hls.service.HlsService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HlsController {
    private final HlsService hlsService;
    private final ResponseService responseService;

    @ApiOperation(value = "m3u8 파일로 변환")
    @PreAuthorize("permitAll()")
    @PostMapping("front/hls/{fileName}")
    public CommonResult convertToM3u8(@PathVariable("fileName") String fileName){
        hlsService.convertToM3u8(fileName);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "m3u8 파일 경로 반환")
    @PreAuthorize("permitAll()")
    @GetMapping("front/hls/{fileName}")
    public SingleResult<String> videoHlsM3U8(@PathVariable("fileName") String fileName) {
        return responseService.getSingleResult(hlsService.getM3u8Url(fileName));
    }
}
