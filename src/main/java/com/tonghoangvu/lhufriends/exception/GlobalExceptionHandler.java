package com.tonghoangvu.lhufriends.exception;

import com.tonghoangvu.lhufriends.common.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    protected ResponseEntity<ErrorResponse> handleAppException(AppException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(e.getErrorCode())
                .message(e.getMessage())  // Managed exceptions message
                .build();
        return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ErrorResponse> handleBadCredentialsException(
            BadCredentialsException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ErrorCode.BAD_CREDENTIALS)
                .message(e.getMessage())  // Modified exception message
                .build();
        return ResponseEntity.status(401).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ErrorCode.ACCESS_DENIED)
                .message(e.getMessage())  // Modified exception message
                .build();
        return ResponseEntity.status(403).body(errorResponse);
    }

    @ExceptionHandler(LockedException.class)
    protected ResponseEntity<ErrorResponse> handleLockedException(LockedException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ErrorCode.ACCOUNT_LOCKED)
                .message(e.getMessage())  // Modified exception message
                .build();
        return ResponseEntity.status(403).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleUnwantedException(Exception e) {
        e.printStackTrace();  // Critical error, print details
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ErrorCode.SERVER_ERROR)
                .message("Internal Server Error")  // Don't send exception message to client
                .build();
        return ResponseEntity.status(500).body(errorResponse);
    }
}
