package com.insightweb.auth.domain

data class User(
    val id: Long?,
    val email: String,
    val password: String,
    val roles: Set<Role>
)
