package com.tonghoangvu.lhufriends.component;

import org.jetbrains.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
    private @Nullable Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public @Nullable String getUsername() {
        Authentication authentication = getAuthentication();
        return authentication == null ? null : authentication.getName();
    }
}
