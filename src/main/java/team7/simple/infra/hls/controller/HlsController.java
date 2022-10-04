package team7.simple.infra.hls.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team7.simple.global.common.response.dto.CommonResult;
import team7.simple.global.common.response.dto.SingleResult;
import team7.simple.global.common.response.service.ResponseService;
import team7.simple.infra.hls.dto.HlsRequestDto;
import team7.simple.infra.hls.service.HlsService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HlsController {
    private final HlsService hlsService;
    private final ResponseService responseService;

    @ApiOperation(value = "m3u8 파일로 변환")
    @PreAuthorize("permitAll()")
    @PostMapping("front/hls")
    public CommonResult convertToM3u8(@RequestBody HlsRequestDto hlsRequestDto){
        hlsService.convertToM3u8(hlsRequestDto);
        return responseService.getSuccessResult();
    }

    @ApiOperation(value = "m3u8 파일 경로 반환")
    @PreAuthorize("permitAll()")
    @GetMapping("front/hls")
    public SingleResult<String> videoHlsM3U8(@RequestBody HlsRequestDto hlsRequestDto) {
        return responseService.getSingleResult(hlsService.getM3u8Url(hlsRequestDto));
    }
}
