package com.tonghoangvu.lhufriends.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "Username must be not empty")
    private String username;

    @NotNull(message = "Password is required")
    @Min(value = 8, message = "Password must be longer than 8 letters")
    private String password;

    @NotBlank(message = "Display name must be not empty")
    private String displayName;
}
