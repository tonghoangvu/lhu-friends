package com.tonghoangvu.lhufriends.dto.response;

import com.tonghoangvu.lhufriends.model.response.TokenResponse;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class TokenResponseDto {
    private final @NotNull String token;

    public TokenResponseDto(@NotNull TokenResponse model) {
        this.token = model.getToken();
    }
}
