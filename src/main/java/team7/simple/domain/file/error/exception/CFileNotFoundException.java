package team7.simple.domain.file.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team7.simple.global.error.ErrorCode;

@AllArgsConstructor
@Getter
public class CFileNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public CFileNotFoundException() {
        super();
        errorCode = ErrorCode.FILE_NOT_FOUND_EXCEPTION;
    }
}
