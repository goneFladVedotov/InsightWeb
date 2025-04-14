package com.insight_web.blackbox.adapters.rest.auth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthProvider {
    public String getEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
