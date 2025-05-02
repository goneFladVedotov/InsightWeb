package com.insightweb.auth.adapters.rest.dto

import com.insightweb.auth.domain.Role

data class UserDto(
    val id: Long,
    val email: String,
    val password: String,
    val role: List<Role>,
)
