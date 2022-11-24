package team7.simple.domain.player.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team7.simple.domain.player.dto.ExitRequestDto;
import team7.simple.domain.player.dto.StartRequestDto;
import team7.simple.domain.player.service.PlayerService;
import team7.simple.domain.unit.dto.UnitPlayRequestDto;
import team7.simple.domain.user.entity.User;
import team7.simple.global.common.ConstValue;

@Api(tags = {"[Front API] Player"})
@Controller
@RequiredArgsConstructor
@RequestMapping("/front")
public class PlayerController {

    private final PlayerService playerService;

    @ApiOperation(value = "FRONT - 플레이어 시작", notes = "플레이어가 실행되자마자 가장 먼저 호출되어야 하는 API로, userId에 해당하는 어세스 토큰을 반환합니다.")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 사용자를 찾을 수 없을 경우"),
    })
    @PreAuthorize("permitAll()")
    @PostMapping("/start")
    public ResponseEntity<?> start(@RequestBody StartRequestDto startRequestDto) {
        return new ResponseEntity<>(playerService.initPlayer(startRequestDto), HttpStatus.OK);
    }

    @ApiOperation(value = "FRONT - 강의 재생", notes = "다른 강의로 이동할 경우 호출하는 API입니다. 현재 재생중인 강의의 시간대와 완료 여부를 기록한 후, 다음 강의 영상의 경로를 포함한 정보를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 강의가 없을 경우"),
    })
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/play/units/{nextUnitId}/")
    public ResponseEntity<?> playUnit(@PathVariable Long nextUnitId, @RequestBody UnitPlayRequestDto unitPlayRequestDto, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(playerService.playUnit(nextUnitId, unitPlayRequestDto, user), HttpStatus.OK);
    }

    @ApiOperation(value = "TEST - 강의 재생 (로컬 테스트용)", notes = "로컬 테스트용입니다")
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/play-local/units/{nextUnitId}")
    public ResponseEntity<?> playUnitInLocal(@PathVariable Long nextUnitId, @RequestBody UnitPlayRequestDto unitPlayRequestDto, @AuthenticationPrincipal User user) {
        return new ResponseEntity<>(playerService.playUnitInLocal(nextUnitId, unitPlayRequestDto, user), HttpStatus.OK);
    }

    @ApiOperation(value = "FRONT - 플레이어 종료", notes = "플레이어가 종료될 때 호출되어야 하는 API로, 현재 재생중인 강의의 시청 기록을 갱신합니다.")
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code=200, message = "성공"),
            @ApiResponse(code=404, message = "해당 강의을 찾을 수 없을 경우"),
    })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/exit")
    public ResponseEntity<?> exit(@RequestHeader(ConstValue.JWT_HEADER) String accessToken, @RequestBody ExitRequestDto exitRequestDto) {
        playerService.exitPlayer(accessToken, exitRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
