package com.tonghoangvu.lhufriends.model.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public class TokenRequest {
    private final @NotNull String username;
    private final @NotNull String password;
}
