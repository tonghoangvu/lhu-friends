package com.tonghoangvu.lhufriends.service;

import com.tonghoangvu.lhufriends.common.UserRole;
import com.tonghoangvu.lhufriends.dto.UserDto;
import com.tonghoangvu.lhufriends.entity.User;
import com.tonghoangvu.lhufriends.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private final UserDetailsService userDetailsService;
    private final JwtTokenService jwtTokenService;

    private final UserRepository userRepository;

    public String generateToken(UserDto userDto) {
        // Only get username & password
        UserDetails userDetails = userDetailsService
                .loadUserByUsername(userDto.getUsername());

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(new Date());
        Set<UserRole> roles = new HashSet<>();
        roles.add(UserRole.ROLE_USER);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public User getUser(String username) {
        User user = userRepository.findFirstByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("Username not found");
        return user;
    }
}
