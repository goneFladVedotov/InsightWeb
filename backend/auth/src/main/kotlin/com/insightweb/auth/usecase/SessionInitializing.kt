package com.insightweb.auth.usecase

import com.insightweb.auth.adapters.auth.JwtTokenUtils
import com.insightweb.auth.adapters.auth.JwtUserDetailsHandler
import com.insightweb.auth.domain.AuthToken
import com.insightweb.auth.usecase.action.LoginAction
import jakarta.transaction.Transactional
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class SessionInitializing(
    var authenticationManager: AuthenticationManager,
    var jwtUtils: JwtTokenUtils,
    var jwtUserDetailsHandler: JwtUserDetailsHandler,
) {
    @Transactional
    fun initialize(action: LoginAction): AuthToken {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(action.email, action.password))
        val userDetails: UserDetails = jwtUserDetailsHandler.loadUserByUsername(action.email)
        val token = jwtUtils.generateToken(userDetails)
        return AuthToken(token)
    }
}