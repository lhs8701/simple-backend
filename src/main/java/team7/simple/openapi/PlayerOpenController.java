package team7.simple.openapi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team7.simple.domain.player.dto.ExecuteRequestDto;
import team7.simple.domain.player.service.PlayerService;
import team7.simple.domain.user.entity.User;
import team7.simple.global.common.ConstValue;

import java.net.URISyntaxException;
@Api(tags = {"[Open API] Player"})
@Controller
@RequiredArgsConstructor
@RequestMapping("/open/player")
public class PlayerOpenController {
    private final PlayerService playerService;

    @ApiOperation(value = "OPEN - 플레이어 실행", notes = "플레이어의 URL로 이동합니다.")
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/execute")
    public ResponseEntity<?> executePlayer(@RequestHeader(ConstValue.JWT_HEADER) String accessToken, @RequestBody ExecuteRequestDto executeRequestDto, @AuthenticationPrincipal User user) throws URISyntaxException {
        return new ResponseEntity<>(playerService.executePlayer(accessToken, executeRequestDto, user), HttpStatus.OK);
    }

}
