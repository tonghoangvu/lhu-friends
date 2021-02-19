package com.tonghoangvu.lhufriends.model.request;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class TokenRequest {
    private final String username;
    private final String password;

    public TokenRequest(@NotNull UserRequest dto) {
        this.username = dto.getUsername();
        this.password = dto.getPassword();
    }
}
