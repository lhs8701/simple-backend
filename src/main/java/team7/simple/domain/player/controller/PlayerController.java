package team7.simple.domain.player.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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

import java.net.URI;
import java.net.URISyntaxException;

@Api(tags = {"Player Controller"})
@Controller
@RequiredArgsConstructor
@Slf4j
public class PlayerController {

    private final PlayerService playerService;

    @ApiOperation(value = "OPEN - 플레이어 실행", notes = "플레이어의 URL로 이동합니다.")
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/open/player/execute")
    public ResponseEntity<?> executePlayer(@RequestHeader(ConstValue.JWT_HEADER) String accessToken, @RequestBody ExecuteRequestDto executeRequestDto, @AuthenticationPrincipal User user) throws URISyntaxException {
        URI redirectedUri = playerService.executePlayer(accessToken, executeRequestDto, user);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectedUri);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

    @ApiOperation(value = "FRONT - 플레이어 시작", notes = "플레이어가 실행되자마자 가장 먼저 호출되어야 하는 API로, userId에 해당하는 어세스 토큰을 반환합니다.")
    @ResponseBody
    @PreAuthorize("permitAll()")
    @PostMapping("/front/player/on")
    public ResponseEntity<?> start(@RequestBody StartRequestDto startRequestDto) {
        playerService.start(startRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "FRONT - 플레이어 종료", notes = "플레이어가 종료될 때 호출되어야 하는 API로, 현재 재생중인 강의의 시청 기록을 갱신합니다.")
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/front/player/off")
    public ResponseEntity<?> exit(@RequestHeader(ConstValue.JWT_HEADER) String accessToken, @RequestBody ExitRequestDto exitRequestDto) {
        playerService.exit(accessToken, exitRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
