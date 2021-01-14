package com.tonghoangvu.lhufriends.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "DTO chứa data user")
public class UserDto {
    @NotBlank(message = "Username must be not empty")
    @ApiModelProperty(notes = "Username là duy nhất cho mỗi user", example = "tonghoangvu")
    private String username;

    @NotNull(message = "Password is required")
    @Min(value = 8, message = "Password must be longer than 8 letters")
    private String password;

    @NotBlank(message = "Display name must be not empty")
    private String displayName;
}
