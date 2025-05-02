package com.insightweb.auth.usecase.storage

import com.insightweb.auth.domain.User

interface UserStorage {
    fun create(user: User): User

    fun findById(id: Long): User

    fun findByEmail(email: String): User

    fun existsByEmail(email: String): Boolean
}