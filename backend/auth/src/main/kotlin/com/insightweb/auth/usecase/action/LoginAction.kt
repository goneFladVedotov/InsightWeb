package com.insightweb.auth.usecase.action

data class LoginAction(
    val email: String,
    val password: String,
)
