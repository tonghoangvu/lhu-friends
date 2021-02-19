package com.tonghoangvu.lhufriends.service;

import com.tonghoangvu.lhufriends.common.UserRole;
import com.tonghoangvu.lhufriends.entity.User;
import com.tonghoangvu.lhufriends.model.request.TokenRequest;
import com.tonghoangvu.lhufriends.model.request.UserRequest;
import com.tonghoangvu.lhufriends.model.response.TokenResponse;
import com.tonghoangvu.lhufriends.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final @NotNull AuthenticationManager authenticationManager;
    private final @NotNull PasswordEncoder passwordEncoder;

    private final @NotNull UserDetailsService userDetailsService;
    private final @NotNull JwtTokenService jwtTokenService;

    private final @NotNull UserRepository userRepository;

    private @NotNull User getUserOrExitWithException(String username) {
        User user = userRepository.findFirstByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("Username not found");
        return user;
    }

    public @NotNull TokenResponse generateToken(@NotNull TokenRequest tokenRequest) {
        // Load existed user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(tokenRequest.getUsername());

        // Modify BadCredentialsException message
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            tokenRequest.getUsername(), tokenRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Username and password do not match");
        }

        // Generate access token
        String token = jwtTokenService.generateToken(userDetails);
        return new TokenResponse(token);
    }

    public @NotNull User createUser(@NotNull UserRequest userRequest) {
        User user = new User(userRequest);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(UserRole.ROLE_USER));
        user.setDeleted(false);
        return userRepository.save(user);
    }

    public @NotNull List<User> getUserList(int page, int size) {
        Page<User> userInPage = userRepository.findAll(PageRequest.of(page, size));
        return userInPage.toList();
    }

    public @NotNull User getUser(String username) {
        return getUserOrExitWithException(username);
    }

    public @NotNull User updateUser(String username, @NotNull UserRequest userRequest) {
        User user = getUserOrExitWithException(username);

        // Update not null fields (exclude password)
        // Required fields are not allow empty value, but optional fields can be set empty value
        if (userRequest.getUsername() != null)
            user.setUsername(userRequest.getUsername());
        if (userRequest.getDisplayName() != null)
            user.setDisplayName(userRequest.getDisplayName());
        if (userRequest.getGender() != null)
            user.setGender(userRequest.getGender());
        if (userRequest.getBio() != null)
            user.setBio(userRequest.getBio());
        if (userRequest.getBirthday() != null)
            user.setBirthday(userRequest.getBirthday());
        if (userRequest.getEmail() != null)
            user.setEmail(userRequest.getEmail());
        if (userRequest.getPhone() != null)
            user.setPhone(userRequest.getPhone());

        user.setUpdatedAt(new Date());
        return userRepository.save(user);
    }

    public void softDeleteUser(String username) {
        User user = getUserOrExitWithException(username);
        user.setDeleted(true);
        user.setUpdatedAt(new Date());
        userRepository.save(user);
    }

    public @NotNull User updateUserRole(String username, @NotNull Set<UserRole> roles) {
        User user = getUserOrExitWithException(username);
        user.setRoles(roles);
        user.setUpdatedAt(new Date());
        return userRepository.save(user);
    }
}
