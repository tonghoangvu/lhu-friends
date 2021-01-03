package com.tonghoangvu.lhufriends.exception;

import com.tonghoangvu.lhufriends.common.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private final ErrorCode code;
    private final String message;
}
