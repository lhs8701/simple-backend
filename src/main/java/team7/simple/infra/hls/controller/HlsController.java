package team7.simple.infra.hls.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import team7.simple.global.common.response.dto.SingleResult;
import team7.simple.global.common.response.service.ResponseService;
import team7.simple.infra.hls.service.HlsService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HlsController {
    private final HlsService hlsService;
    private final ResponseService responseService;


    /* m3u8 파일 경로 반환 */
    @PreAuthorize("permitAll()")
    @GetMapping("front/hls/{fileName}")
    public SingleResult<String> videoHlsM3U8(@PathVariable String fileName) {
        return responseService.getSingleResult(hlsService.getM3u8Url(fileName));
    }
}
