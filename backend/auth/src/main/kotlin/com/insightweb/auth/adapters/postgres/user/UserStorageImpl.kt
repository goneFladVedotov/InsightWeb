package com.insightweb.auth.adapters.postgres.user

import com.insightweb.auth.domain.User
import com.insightweb.auth.domain.exception.ResourceNotFoundException
import com.insightweb.auth.usecase.storage.UserStorage
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class UserStorageImpl(
    private val userRepository: UserRepository,
): UserStorage {
    override fun create(user: User): User =
        userRepository.save(UserEntity().fillForSaving(user)).toDomainModel()

    override fun findById(id: Long): User = userRepository.findById(id).getOrNull()?.toDomainModel()
        ?: throw ResourceNotFoundException("user by $id not found")

    override fun findByEmail(email: String): User = userRepository.findByEmail(email)?.toDomainModel() ?: throw ResourceNotFoundException("user by email $email not found")

    override fun existsByEmail(email: String): Boolean = userRepository.existsByEmail(email)
}