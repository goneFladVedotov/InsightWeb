package com.insightweb.auth.adapters.postgres.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?

    fun existsByEmail(email: String):Boolean
}