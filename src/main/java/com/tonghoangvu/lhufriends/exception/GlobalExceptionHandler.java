package com.tonghoangvu.lhufriends.exception;

import com.tonghoangvu.lhufriends.common.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    protected ResponseEntity<ErrorResponse> handleAppException(AppException exception) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(exception.getErrorCode())
                .message(exception.getMessage())
                    // Return to client, because these exceptions are managed
                .build();
        return ResponseEntity.status(exception.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleUnwantedException(Exception exception) {
        // Print exception details
        exception.printStackTrace();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(ErrorCode.SERVER_ERROR)
                .message("Internal server error")  // Don't return exception message to client
                .build();
        return ResponseEntity.status(500).body(errorResponse);
    }
}
