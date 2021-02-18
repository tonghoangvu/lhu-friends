package com.tonghoangvu.lhufriends.controller;

import com.tonghoangvu.lhufriends.common.UserRole;
import com.tonghoangvu.lhufriends.dto.request.UserRequestDto;
import com.tonghoangvu.lhufriends.dto.response.TokenResponseDto;
import com.tonghoangvu.lhufriends.dto.response.UserInfoDto;
import com.tonghoangvu.lhufriends.entity.User;
import com.tonghoangvu.lhufriends.model.request.TokenRequest;
import com.tonghoangvu.lhufriends.model.response.TokenResponse;
import com.tonghoangvu.lhufriends.service.UserService;
import com.tonghoangvu.lhufriends.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tonghoangvu.lhufriends.common.ValidationProfiles.OnAuth;
import static com.tonghoangvu.lhufriends.common.ValidationProfiles.OnCreate;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final @NotNull UserService userService;

    @PostMapping("/auth")
    public @NotNull ResponseEntity<TokenResponseDto> authenticate(
            @Validated(OnAuth.class) @RequestBody @NotNull UserRequestDto userRequestDto,
            @NotNull BindingResult bindingResult) {
        ControllerUtil.handleBindingError(bindingResult);
        TokenRequest tokenRequest = new TokenRequest(userRequestDto.getUsername(), userRequestDto.getPassword());
        TokenResponse tokenResponse = userService.generateToken(tokenRequest);
        TokenResponseDto tokenResponseDto = new TokenResponseDto(tokenResponse);
        return ResponseEntity.ok(tokenResponseDto);
    }

    @PostMapping("/")
    public @NotNull ResponseEntity<UserInfoDto> createUser(
            @Validated(OnCreate.class) @RequestBody @NotNull UserRequestDto userRequestDto,
            @NotNull BindingResult bindingResult) {
        ControllerUtil.handleBindingError(bindingResult);
        User user = new User(userRequestDto);
        User createdUser = userService.createUser(user);
        UserInfoDto userInfoDto = new UserInfoDto(createdUser);
        return ResponseEntity.ok(userInfoDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/")
    public @NotNull ResponseEntity<List<UserInfoDto>> getAllUser(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        List<User> userList = userService.getUserList(page, size);
        List<UserInfoDto> userInfoDtoList = userList.stream()
                .map(UserInfoDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userInfoDtoList);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/")
    public @NotNull ResponseEntity<Object> deleteAnyUser(
            @RequestParam("username") @NotNull String username) {
        userService.softDeleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/role")
    public @NotNull ResponseEntity<UserInfoDto> updateAnyUserRole(
            @RequestParam("username") String username,
            @RequestParam("roles") @NotNull Set<UserRole> roles) {
        User user = userService.updateUserRole(username, roles);
        UserInfoDto userInfoDto = new UserInfoDto(user);
        return ResponseEntity.ok(userInfoDto);
    }
}
