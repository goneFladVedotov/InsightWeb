package com.insight_web.blackbox.usecase;

import com.insight_web.blackbox.adapters.auth.JwtUserDetailsService;
import com.insight_web.blackbox.adapters.auth.JwtUtils;
import com.insight_web.blackbox.domain.AuthToken;
import com.insight_web.blackbox.usecase.action.LoginAction;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionInitializing {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final JwtUserDetailsService jwtUserDetailsService;

    @Transactional
    public AuthToken initialize(LoginAction action) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(action.getEmail(), action.getPassword()));
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(action.getEmail());
        var token = jwtUtils.generateToken(userDetails);
        return new AuthToken(token);
    }
}
