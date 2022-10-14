package team7.simple.domain.player.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "OPEN - 플레이어 실행")
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/open/player/execute")
    public ResponseEntity<?> executePlayer(@RequestHeader(ConstValue.JWT_HEADER) String accessToken, @RequestBody ExecuteRequestDto executeRequestDto) {
        return new ResponseEntity<>(playerService.executePlayer(accessToken, executeRequestDto), HttpStatus.SEE_OTHER);
    }

    @ApiOperation(value = "FRONT - 플레이어 시작")
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/front/player/on")
    public ResponseEntity<?> start(@RequestHeader(ConstValue.JWT_HEADER) String accessToken, @RequestBody StartRequestDto startRequestDto, @AuthenticationPrincipal User user) {
        playerService.start(accessToken, startRequestDto, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "FRONT - 플레이어 종료")
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/front/player/off")
    public ResponseEntity<?> exit(@RequestHeader(ConstValue.JWT_HEADER) String accessToken, @RequestBody ExitRequestDto exitRequestDto) {
        playerService.exit(accessToken, exitRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
