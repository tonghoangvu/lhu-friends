package com.tonghoangvu.lhufriends.service;

import com.tonghoangvu.lhufriends.common.UserRole;
import com.tonghoangvu.lhufriends.entity.UserEntity;
import com.tonghoangvu.lhufriends.model.request.TokenRequest;
import com.tonghoangvu.lhufriends.model.request.UserRequest;
import com.tonghoangvu.lhufriends.model.response.TokenResponse;
import com.tonghoangvu.lhufriends.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
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
    private final @NotNull ModelMapper modelMapper;
    private final @NotNull AuthenticationManager authenticationManager;
    private final @NotNull PasswordEncoder passwordEncoder;

    private final @NotNull UserDetailsService userDetailsService;
    private final @NotNull JwtTokenService jwtTokenService;

    private final @NotNull UserRepository userRepository;

    private @NotNull UserEntity getUserOrExitWithException(String username) {
        UserEntity userEntity = userRepository.findFirstByUsername(username);
        if (userEntity == null)
            throw new UsernameNotFoundException("Username not found");
        return userEntity;
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

    public @NotNull UserEntity createUser(@NotNull UserRequest userRequest) {
        UserEntity userEntity = modelMapper.map(userRequest, UserEntity.class);
        userEntity.setCreatedAt(new Date());
        userEntity.setUpdatedAt(new Date());
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(Collections.singleton(UserRole.ROLE_USER));
        userEntity.setDeleted(false);
        return userRepository.save(userEntity);
    }

    public @NotNull List<UserEntity> getUserList(int page, int size) {
        Page<UserEntity> userInPage = userRepository.findAll(PageRequest.of(page, size));
        return userInPage.toList();
    }

    public @NotNull UserEntity getUser(String username) {
        return getUserOrExitWithException(username);
    }

    public @NotNull UserEntity updateUser(String username, @NotNull UserRequest userRequest) {
        UserEntity userEntity = getUserOrExitWithException(username);

        // Update not null fields (exclude password)
        // Required fields are not allow empty value, but optional fields can be set empty value
        if (userRequest.getUsername() != null)
            userEntity.setUsername(userRequest.getUsername());
        if (userRequest.getDisplayName() != null)
            userEntity.setDisplayName(userRequest.getDisplayName());
        if (userRequest.getGender() != null)
            userEntity.setGender(userRequest.getGender());
        if (userRequest.getBio() != null)
            userEntity.setBio(userRequest.getBio());
        if (userRequest.getBirthday() != null)
            userEntity.setBirthday(userRequest.getBirthday());
        if (userRequest.getEmail() != null)
            userEntity.setEmail(userRequest.getEmail());
        if (userRequest.getPhone() != null)
            userEntity.setPhone(userRequest.getPhone());

        userEntity.setUpdatedAt(new Date());
        return userRepository.save(userEntity);
    }

    public void softDeleteUser(String username) {
        UserEntity userEntity = getUserOrExitWithException(username);
        userEntity.setDeleted(true);
        userEntity.setUpdatedAt(new Date());
        userRepository.save(userEntity);
    }

    public @NotNull UserEntity updateUserRole(String username, @NotNull Set<UserRole> roles) {
        UserEntity userEntity = getUserOrExitWithException(username);
        userEntity.setRoles(roles);
        userEntity.setUpdatedAt(new Date());
        return userRepository.save(userEntity);
    }
}
