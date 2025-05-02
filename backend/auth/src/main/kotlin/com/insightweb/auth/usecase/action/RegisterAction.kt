package com.insightweb.auth.usecase.action

import com.insightweb.auth.domain.User

data class RegisterAction(
    val login: String,
    val password: String,
    val passwordConfirmation: String
) {
    fun toUser(): User = User(
        id = null,
        email = login,
        password = password,
        roles = emptySet()
    )
}
