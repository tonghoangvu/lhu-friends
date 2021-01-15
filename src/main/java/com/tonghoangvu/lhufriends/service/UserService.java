package com.tonghoangvu.lhufriends.service;

import com.tonghoangvu.lhufriends.common.UserRole;
import com.tonghoangvu.lhufriends.dto.UserDto;
import com.tonghoangvu.lhufriends.entity.User;
import com.tonghoangvu.lhufriends.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;
    private final JwtTokenService jwtTokenService;

    private final UserRepository userRepository;

    private User getUserOrExitWithException(String username) {
        User user = userRepository.findFirstByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("Username not found");
        return user;
    }

    public String generateToken(UserDto userDto) {
        // Load existed user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());

        // Modify BadCredentialsException message
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDto.getUsername(), userDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Username and password do not match");
        }

        // Generate access token
        return jwtTokenService.generateToken(userDetails);
    }

    public User createUser(UserDto userDto) {
        User user = new User(userDto);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(UserRole.ROLE_USER));
        user.setDeleted(false);
        return userRepository.save(user);
    }

    public List<User> getUserList(int page, int size) {
        Page<User> userInPage = userRepository.findAll(PageRequest.of(page, size));
        return userInPage.toList();
    }

    public User getUser(String username) {
        return getUserOrExitWithException(username);
    }

    public User updateUser(String username, UserDto userDto) {
        User user = getUserOrExitWithException(username);

        // Update not null fields (exclude password)
        // Required fields are not allow empty value, but optional fields can be set empty value
        if (userDto.getUsername() != null)
            user.setUsername(userDto.getUsername());
        if (userDto.getDisplayName() != null)
            user.setDisplayName(userDto.getDisplayName());
        if (userDto.getGender() != null)
            user.setGender(userDto.getGender());
        if (userDto.getBio() != null)
            user.setBio(userDto.getBio());
        if (userDto.getBirthday() != null)
            user.setBirthday(userDto.getBirthday());
        if (userDto.getEmail() != null)
            user.setEmail(userDto.getEmail());
        if (userDto.getPhone() != null)
            user.setPhone(userDto.getPhone());

        user.setUpdatedAt(new Date());
        return userRepository.save(user);
    }

    public void softDeleteUser(String username) {
        User user = getUserOrExitWithException(username);
        user.setDeleted(true);
        user.setUpdatedAt(new Date());
        userRepository.save(user);
    }

    public User updateUserRole(String username, Set<UserRole> roles) {
        User user = getUserOrExitWithException(username);
        user.setRoles(roles);
        user.setUpdatedAt(new Date());
        return userRepository.save(user);
    }
}
