package com.tonghoangvu.lhufriends.controller;

import com.tonghoangvu.lhufriends.component.AuthenticationFacade;
import com.tonghoangvu.lhufriends.dto.UserDto;
import com.tonghoangvu.lhufriends.model.TokenModel;
import com.tonghoangvu.lhufriends.model.UserModel;
import com.tonghoangvu.lhufriends.service.UserService;
import com.tonghoangvu.lhufriends.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.tonghoangvu.lhufriends.common.ValidationProfiles.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationFacade authenticationFacade;
    private final UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<TokenModel> authenticate(
            @Validated(OnAuth.class) @RequestBody UserDto userDto,
            final BindingResult bindingResult) {
        ControllerUtil.handleBindingError(bindingResult);
        String token = userService.generateToken(userDto);
        return ResponseEntity.ok(new TokenModel(token));
    }

    @PostMapping("/")
    public ResponseEntity<UserModel> createUser(
            @Validated(OnCreate.class) @RequestBody UserDto userDto,
            final BindingResult bindingResult) {
        ControllerUtil.handleBindingError(bindingResult);
        UserModel createdUser = new UserModel(userService.createUser(userDto));
        return ResponseEntity.ok(createdUser);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/")
    public ResponseEntity<UserModel> updateUser(
            @Validated(OnUpdate.class) @RequestBody UserDto userDto,
            final BindingResult bindingResult) {
        ControllerUtil.handleBindingError(bindingResult);
        String username = authenticationFacade.getUsername();
        UserModel updatedUser = new UserModel(userService.updateUser(username, userDto));
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("/me")
    public ResponseEntity<UserModel> getMyInfo() {
        String username = authenticationFacade.getUsername();
        UserModel myUser = new UserModel(userService.getUser(username));
        return ResponseEntity.ok(myUser);
    }
}
