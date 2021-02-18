package com.tonghoangvu.lhufriends.model.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public class TokenResponse {
    private final @NotNull String token;
}
