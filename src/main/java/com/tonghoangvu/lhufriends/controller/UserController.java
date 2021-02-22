package com.tonghoangvu.lhufriends.controller;

import com.tonghoangvu.lhufriends.common.UserRole;
import com.tonghoangvu.lhufriends.entity.UserEntity;
import com.tonghoangvu.lhufriends.model.request.TokenRequest;
import com.tonghoangvu.lhufriends.model.request.UserRequest;
import com.tonghoangvu.lhufriends.model.response.TokenResponse;
import com.tonghoangvu.lhufriends.model.response.UserInfo;
import com.tonghoangvu.lhufriends.service.UserService;
import com.tonghoangvu.lhufriends.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tonghoangvu.lhufriends.common.ValidationProfiles.OnCreate;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final @NotNull ModelMapper modelMapper;
    private final @NotNull UserService userService;

    @PostMapping("/auth")
    public @NotNull ResponseEntity<TokenResponse> authenticate(
            @RequestBody @NotNull TokenRequest tokenRequest,
            @NotNull BindingResult bindingResult) {
        ControllerUtil.handleBindingError(bindingResult);
        TokenResponse tokenResponse = userService.generateToken(tokenRequest);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/")
    public @NotNull ResponseEntity<UserInfo> createUser(
            @Validated(OnCreate.class) @RequestBody @NotNull UserRequest userRequest,
            @NotNull BindingResult bindingResult) {
        ControllerUtil.handleBindingError(bindingResult);
        UserEntity createdUserEntity = userService.createUser(userRequest);
        UserInfo userInfo = modelMapper.map(createdUserEntity, UserInfo.class);
        return ResponseEntity.ok(userInfo);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/")
    public @NotNull ResponseEntity<List<UserInfo>> getAllUser(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        List<UserEntity> userEntityList = userService.getUserList(page, size);
        List<UserInfo> userInfoList = userEntityList.stream()
                .map(userEntity -> modelMapper.map(userEntity, UserInfo.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(userInfoList);
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
    public @NotNull ResponseEntity<UserInfo> updateAnyUserRole(
            @RequestParam("username") String username,
            @RequestParam("roles") @NotNull Set<UserRole> roles) {
        UserEntity userEntity = userService.updateUserRole(username, roles);
        UserInfo userInfo = modelMapper.map(userEntity, UserInfo.class);
        return ResponseEntity.ok(userInfo);
    }
}
