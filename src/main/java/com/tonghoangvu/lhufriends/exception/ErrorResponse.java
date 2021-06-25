package com.tonghoangvu.lhufriends.exception;

import com.tonghoangvu.lhufriends.common.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final @NotNull ErrorCode code;
    private final @NotNull String message;
}
