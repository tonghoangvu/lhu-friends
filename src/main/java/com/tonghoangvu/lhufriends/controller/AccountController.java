package com.tonghoangvu.lhufriends.controller;

import com.tonghoangvu.lhufriends.common.ValidationProfiles;
import com.tonghoangvu.lhufriends.component.AuthenticationFacade;
import com.tonghoangvu.lhufriends.dto.UserDto;
import com.tonghoangvu.lhufriends.model.UserModel;
import com.tonghoangvu.lhufriends.service.UserService;
import com.tonghoangvu.lhufriends.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AuthenticationFacade authenticationFacade;
    private final UserService userService;

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/")
    public ResponseEntity<UserModel> getMyAccountInfo() {
        String myUsername = authenticationFacade.getUsername();
        UserModel myUser = new UserModel(userService.getUser(myUsername));
        return ResponseEntity.ok(myUser);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/")
    public ResponseEntity<UserModel> updateMyAccount(
            @Validated(ValidationProfiles.OnUpdate.class) @RequestBody UserDto userDto,
            final BindingResult bindingResult) {
        ControllerUtil.handleBindingError(bindingResult);
        String myUsername = authenticationFacade.getUsername();
        UserModel updatedUser = new UserModel(userService.updateUser(myUsername, userDto));
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/")
    public ResponseEntity<Object> deleteMyAccount() {
        String myUsername = authenticationFacade.getUsername();
        userService.softDeleteUser(myUsername);
        return ResponseEntity.noContent().build();
    }
}
