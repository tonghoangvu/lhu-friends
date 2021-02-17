package com.tonghoangvu.lhufriends.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public class TokenModel {
    private final @NotNull String token;
}
