package team7.simple.domain.auth.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import team7.simple.domain.auth.dto.ResolveConflictRequestDto;
import team7.simple.domain.auth.service.ConflictService;

@Api(tags = {"[Front API] Authentication"})
@RequiredArgsConstructor
@RequestMapping("/front/auth")
@Controller
public class AuthController {
    private final ConflictService conflictService;

    @ApiOperation(value = "FRONT - 로그인 충돌 시, 조치 방법 선택", notes = "같은 계정으로 다수가 로그인할 경우, 사용자의 입력을 받아 충돌을 해결합니다. " +
            "keepGoing값이 참일 경우 기존 이용중이던 사용자가 계속 이용하게 되고, 거짓일 경우 나중에 접속한 사용자가 강제 종료됩니다.")
    @PostMapping(value = "/resolve-conflict")
    public ResponseEntity<?> removeConflict(@RequestBody ResolveConflictRequestDto resolveConflictRequestDto) {
        conflictService.resolveConflict(resolveConflictRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
