package com.insightweb.auth.adapters.rest.auth

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthProvider {
    fun getEmail(): String = SecurityContextHolder.getContext().authentication.name
}