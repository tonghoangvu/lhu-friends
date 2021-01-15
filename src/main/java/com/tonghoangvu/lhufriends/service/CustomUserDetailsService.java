package com.tonghoangvu.lhufriends.service;

import com.tonghoangvu.lhufriends.entity.User;
import com.tonghoangvu.lhufriends.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = userRepository.findFirstByUsername(username);
        if (foundUser == null)
            throw new UsernameNotFoundException("Username not found");

        if (foundUser.isDeleted() == Boolean.TRUE)
            throw new LockedException("Account locked");

        List<GrantedAuthority> authorityList = foundUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                username, foundUser.getPassword(), authorityList);
    }
}
