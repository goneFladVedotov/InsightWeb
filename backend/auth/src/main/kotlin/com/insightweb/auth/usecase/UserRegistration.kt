package com.insightweb.auth.usecase

import com.insightweb.auth.domain.User
import com.insightweb.auth.domain.exception.ResourceNotFoundException
import com.insightweb.auth.usecase.action.RegisterAction
import com.insightweb.auth.usecase.storage.UserStorage
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserRegistration(
    private val userStorage: UserStorage,
) {

    @Transactional
    fun register(action: RegisterAction) {
        if (action.password != action.passwordConfirmation) {
            throw RuntimeException("Password and password does not match")
        }
        if (!userStorage.existsByEmail(action.login)) {
            throw ResourceNotFoundException("user by email " + action.login + "does not found")
        }
        val user: User = action.toUser()
        userStorage.create(user)
    }
}