package com.insightweb.auth.adapters.auth

import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.filter.OncePerRequestFilter

@Service
class AuthJwtTokeFilter(
    private val jwtTokenUtils: JwtTokenUtils
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var token = request.getHeader("Authorization")
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7)
        }

        if (token != null) {
            try {
                val username: String? = jwtTokenUtils.getUsername(token)
                if (username != null) {
                    val authentication = UsernamePasswordAuthenticationToken(
                        username,
                        null, emptyList()
                    )
                    SecurityContextHolder.getContext().authentication = authentication
                }
            } catch (e: JwtException) {
                println("Exception in JWT processing: ${e.message}")
            }
        }

        filterChain.doFilter(request, response)
    }
}