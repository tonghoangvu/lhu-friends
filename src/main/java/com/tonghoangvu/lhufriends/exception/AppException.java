package com.tonghoangvu.lhufriends.exception;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {
    private final int statusCode;
    private final ErrorCode errorCode;
    private final String message;

    public AppException(@NotNull HttpStatus httpStatus, ErrorCode errorCode, String message) {
        super(message);
        this.statusCode = httpStatus.value();
        this.errorCode = errorCode;
        this.message = message;
    }
}
