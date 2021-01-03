package com.tonghoangvu.lhufriends.exception;

import com.tonghoangvu.lhufriends.common.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {
    private final int statusCode;
    private final ErrorCode errorCode;
    private final String message;

    public AppException(HttpStatus httpStatus, ErrorCode errorCode, String message) {
        super(message);
        this.statusCode = httpStatus.value();
        this.errorCode = errorCode;
        this.message = message;
    }
}
