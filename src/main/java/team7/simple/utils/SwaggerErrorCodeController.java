package team7.simple.utils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team7.simple.global.common.response.dto.ErrorResponseDto;
import team7.simple.global.error.ErrorCode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"[Custom Error Code]"})
@Controller
public class SwaggerErrorCodeController {
    @ApiOperation(value = "에러코드 확인용",
            notes = """
                    에러 코드를 조회합니다.
                    """
    )
    @GetMapping("/docs/error-code")
    @ResponseBody
    public List<ErrorResponseDto> displayCustomErrorCode(){

        ErrorCode[] codeList = ErrorCode.values();
        return Arrays.stream(codeList).map(ErrorResponseDto::new).collect(Collectors.toList());
    }
}
