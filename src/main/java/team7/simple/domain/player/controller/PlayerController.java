package team7.simple.domain.player.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import team7.simple.domain.player.dto.ExitRequestDto;
import team7.simple.domain.player.dto.StartRequestDto;
import team7.simple.domain.player.service.PlayerService;
import team7.simple.global.common.response.dto.CommonResult;
import team7.simple.global.common.response.service.ResponseService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PlayerController {

    private final ResponseService responseService;
    private final PlayerService playerService;

    @PostMapping("/open/player/on")
//    @PreAuthorize("hasRole('USER')")
    public String start(@RequestBody StartRequestDto startRequestDto) {
        return "redirect:" + playerService.start(startRequestDto);
    }

    @PostMapping("/open/player/off")
    public CommonResult exit(@RequestBody ExitRequestDto exitRequestDto){
        playerService.exit(exitRequestDto);
        return responseService.getSuccessResult();
    }
}
