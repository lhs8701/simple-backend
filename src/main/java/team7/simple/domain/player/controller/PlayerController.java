package team7.simple.domain.player.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @PostMapping("/open/player/execute")
//    @PreAuthorize("hasRole('USER')")
    public String executePlayer(@RequestHeader("X-AUTH-TOKEN") String accessToken, @RequestBody StartRequestDto startRequestDto) {
        return "redirect:" + playerService.executePlayer(accessToken, startRequestDto);
    }

    @PostMapping("/front/player/on")
//    @PreAuthorize("hasRole('USER')")
    public void start(@RequestHeader("X-AUTH-TOKEN") String accessToken, @RequestBody StartRequestDto startRequestDto) {
        playerService.start(accessToken, startRequestDto);
    }

    @ResponseBody
    @PostMapping("/front/player/off")
    public CommonResult exit(@RequestHeader("X-AUTH-TOKEN") String accessToken, @RequestBody ExitRequestDto exitRequestDto){
        playerService.exit(accessToken, exitRequestDto);
        return responseService.getSuccessResult();
    }
}
