package com.tonghoangvu.lhufriends.controller;

import com.tonghoangvu.lhufriends.dto.UserDto;
import com.tonghoangvu.lhufriends.entity.User;
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

import java.util.List;

import static com.tonghoangvu.lhufriends.common.ValidationProfiles.OnAuth;
import static com.tonghoangvu.lhufriends.common.ValidationProfiles.OnCreate;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
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

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUser(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(userService.getUserList(page, size));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/")
    public ResponseEntity<Object> deleteAnyUser(@RequestParam("username") String username) {
        userService.softDeleteUser(username);
        return ResponseEntity.noContent().build();
    }
}
