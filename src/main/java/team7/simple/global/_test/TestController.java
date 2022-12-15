package team7.simple.global._test;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/test")
@Slf4j
@RequiredArgsConstructor
@ApiIgnore
public class TestController {


    @PreAuthorize("permitAll()")
    @ResponseBody
    @PostMapping("/player/on")
    public ResponseEntity<?> start() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "AccessToken",
                    required = true, dataType = "String", paramType = "header"
            )
    })
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    @GetMapping("/user")
    public String forUser(){
        return "This is only for user";
    }

    @PreAuthorize("permitAll()")
    @ResponseBody
    @GetMapping("/all")
    public String forAll(){
        return "This is for anyone";
    }

    @ApiOperation(value = "TEST - 영상 업로드")
    @PostMapping("upload")
    public ResponseEntity<?> uploadUnit(@RequestPart MultipartFile file) {
        log.info(file.getName());
        return new ResponseEntity<>(file.getName(), HttpStatus.OK);
    }
}
