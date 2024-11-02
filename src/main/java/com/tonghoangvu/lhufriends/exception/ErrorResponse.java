package com.tonghoangvu.lhufriends.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final @NotNull ErrorCode code;
    private final @NotNull String message;
}
