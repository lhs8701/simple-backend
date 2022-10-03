package team7.simple.global.common.response.service;


import org.springframework.stereotype.Service;
import team7.simple.global.common.response.dto.CommonResult;
import team7.simple.global.common.response.dto.ListResult;
import team7.simple.global.common.response.dto.SingleResult;



import java.util.List;

//결과 모델에 데이터를 넣어주는 Service

@Service // 해당 Class가 Service임을 명시합니다.
public class ResponseService {

    // enum으로 api 요청 결과에 대한 code, message를 정의합니다.

    public enum CommonResponse {
        SUCCESS(0, "성공하였습니다."),
        FAIL(-1, "실패하였습니다.");

        int code;
        String msg;

        CommonResponse(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }


    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    // 다중건 결과를 처리하는 메소드
    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        setSuccessResult(result);
        return result;
    }

    public CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    public CommonResult getFailResult(int code, String msg) {
        CommonResult result = new CommonResult();
        setFailResult(result, code, msg);
        return result;
    }

    private void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    public void setFailResult(CommonResult result, int code, String msg) {
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
    }
}
