package com.insightweb.auth.adapters.postgres.user

import com.insightweb.auth.domain.User
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.Set

@Entity
@Table(name = "users")
class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var email: String? = null
    var password: String? = null

    fun fillForSaving(user: User): UserEntity = apply {
        if (user.id != null) {
            id = user.id
        }
        password = user.password
        email = user.email
    }

    fun toDomainModel(): User = User(
        id = requireNotNull(id),
        email = requireNotNull(email),
        password = requireNotNull(password),
        roles = emptySet()
    )
}