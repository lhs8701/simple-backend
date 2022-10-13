package team7.simple.domain.player.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import team7.simple.domain.player.dto.ExecuteRequestDto;
import team7.simple.domain.player.dto.ExitRequestDto;
import team7.simple.domain.player.dto.StartRequestDto;
import team7.simple.domain.player.service.PlayerService;
import team7.simple.domain.user.entity.User;
import team7.simple.global.common.ConstValue;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PlayerController {

    private final PlayerService playerService;

    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/open/player/execute")
    public ResponseEntity<?> executePlayer(@RequestHeader("X-AUTH-TOKEN") String accessToken, @RequestBody ExecuteRequestDto executeRequestDto) {
        return new ResponseEntity<>(playerService.executePlayer(accessToken, executeRequestDto), HttpStatus.SEE_OTHER);
    }

    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/front/player/on")
    public ResponseEntity<?> start(@RequestHeader("X-AUTH-TOKEN") String accessToken, @RequestBody StartRequestDto startRequestDto, @AuthenticationPrincipal User user) {
        playerService.start(accessToken, startRequestDto, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/front/player/off")
    public ResponseEntity<?> exit(@RequestHeader("X-AUTH-TOKEN") String accessToken, @RequestBody ExitRequestDto exitRequestDto){
        playerService.exit(accessToken, exitRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
