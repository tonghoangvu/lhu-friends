package com.tonghoangvu.lhufriends.dto;

import com.tonghoangvu.lhufriends.util.validator.NullOrNotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import static com.tonghoangvu.lhufriends.common.ValidationProfiles.*;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    @NotEmpty(message = "Username must be not empty", groups = { OnAuth.class, OnCreate.class })
    @NullOrNotEmpty(message = "Username must be not empty", groups = { OnUpdate.class })
    private String username;

    @NotEmpty(message = "Password is required", groups = { OnAuth.class, OnCreate.class })
    @Min(value = 8, message = "Password must be longer than 8 letters")
    private String password;

    @NotEmpty(message = "Display name must be not empty", groups = { OnCreate.class })
    @NullOrNotEmpty(message = "Display name must be not empty", groups = { OnUpdate.class })
    private String displayName;
}
