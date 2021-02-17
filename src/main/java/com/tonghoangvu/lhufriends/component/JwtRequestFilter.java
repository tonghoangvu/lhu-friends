package com.tonghoangvu.lhufriends.component;

import com.tonghoangvu.lhufriends.common.Const;
import com.tonghoangvu.lhufriends.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final @NotNull JwtTokenService jwtTokenService;
    private final @NotNull UserDetailsService userDetailsService;

    @Value("${com.app.JWT_AUTH_HEADER}")
    private String TOKEN_HEADER;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
            @NotNull FilterChain chain) throws ServletException, IOException {
        final String TOKEN_HEADER_PREFIX = Const.TOKEN_HEADER_PREFIX + " ";
        final String tokenHeader = request.getHeader(TOKEN_HEADER);

        String token = null, username = null;
        if (tokenHeader != null && tokenHeader.startsWith(TOKEN_HEADER_PREFIX)) {
            token = tokenHeader.substring(TOKEN_HEADER_PREFIX.length());
            username = jwtTokenService.extractUsername(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtTokenService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
}
