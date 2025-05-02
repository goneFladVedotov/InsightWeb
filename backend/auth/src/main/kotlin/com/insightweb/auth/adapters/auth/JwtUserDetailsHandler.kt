package com.insightweb.auth.adapters.auth

import com.insightweb.auth.adapters.postgres.user.UserEntity
import com.insightweb.auth.adapters.postgres.user.UserRepository
import com.insightweb.auth.domain.exception.ResourceNotFoundException
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class JwtUserDetailsHandler(
    private val userRepository: UserRepository
): UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val user: UserEntity = userRepository.findByEmail(email) ?: throw ResourceNotFoundException("user not found")
        return User(
            /* username = */ user.email,
            /* password = */ user.password,
            /* authorities = */ emptyList()
        )
    }
}