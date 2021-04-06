package com.tonghoangvu.lhufriends.model.response;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class TokenResponse {
    private final @NotNull String token;
}
