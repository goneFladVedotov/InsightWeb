package com.insightweb.auth.usecase

import com.insightweb.auth.domain.User
import com.insightweb.auth.usecase.storage.UserStorage
import org.springframework.stereotype.Service

@Service
class UserSelection(
    private val userStorage: UserStorage,
) {
    fun select(email: String): User = userStorage.findByEmail(email)
}