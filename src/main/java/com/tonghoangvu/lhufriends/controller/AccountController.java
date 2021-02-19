package com.tonghoangvu.lhufriends.controller;

import com.tonghoangvu.lhufriends.common.ValidationProfiles;
import com.tonghoangvu.lhufriends.component.AuthenticationFacade;
import com.tonghoangvu.lhufriends.entity.User;
import com.tonghoangvu.lhufriends.model.request.UserRequest;
import com.tonghoangvu.lhufriends.model.response.UserInfo;
import com.tonghoangvu.lhufriends.service.UserService;
import com.tonghoangvu.lhufriends.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final @NotNull AuthenticationFacade authenticationFacade;
    private final @NotNull UserService userService;

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/")
    public @NotNull ResponseEntity<UserInfo> getMyAccountInfo() {
        String myUsername = authenticationFacade.getUsername();
        User myUser = userService.getUser(myUsername);
        UserInfo myUserInfo = new UserInfo(myUser);
        return ResponseEntity.ok(myUserInfo);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/")
    public @NotNull ResponseEntity<UserInfo> updateMyAccount(
            @Validated(ValidationProfiles.OnUpdate.class) @RequestBody @NotNull UserRequest userRequest,
            @NotNull BindingResult bindingResult) {
        ControllerUtil.handleBindingError(bindingResult);
        String myUsername = authenticationFacade.getUsername();
        User myUser = userService.updateUser(myUsername, userRequest);
        UserInfo myUserInfo = new UserInfo(myUser);
        return ResponseEntity.ok(myUserInfo);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/")
    public @NotNull ResponseEntity<Object> deleteMyAccount() {
        String myUsername = authenticationFacade.getUsername();
        userService.softDeleteUser(myUsername);
        return ResponseEntity.noContent().build();
    }
}
