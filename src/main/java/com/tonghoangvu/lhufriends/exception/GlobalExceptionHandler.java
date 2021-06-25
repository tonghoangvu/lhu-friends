package com.tonghoangvu.lhufriends.exception;

import com.tonghoangvu.lhufriends.common.ErrorCode;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    protected @NotNull ResponseEntity<ErrorResponse> handleAppException(@NotNull AppException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getMessage());
        return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected @NotNull ResponseEntity<ErrorResponse> handleBadCredentialsException(
            @NotNull BadCredentialsException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.BAD_CREDENTIALS, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected @NotNull ResponseEntity<ErrorResponse> handleAccessDeniedException(
            @NotNull AccessDeniedException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.ACCESS_DENIED, e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(LockedException.class)
    protected @NotNull ResponseEntity<ErrorResponse> handleLockedException(
            @NotNull LockedException e) {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.ACCOUNT_LOCKED, e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    protected @NotNull ResponseEntity<ErrorResponse> handleUnwantedException(@NotNull Exception e) {
        e.printStackTrace();  // Critical error, print details
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.SERVER_ERROR, "Internal Server Error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
