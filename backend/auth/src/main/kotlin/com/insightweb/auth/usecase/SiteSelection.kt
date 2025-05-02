package com.insightweb.auth.usecase

import com.insightweb.auth.domain.Site
import com.insightweb.auth.usecase.storage.SiteStorage
import com.insightweb.auth.usecase.storage.UserStorage
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class SiteSelection(
    private val siteStorage: SiteStorage,
    private val userStorage: UserStorage,
) {

    fun select(url: String): Site = siteStorage.findById(url)
    @Transactional
    fun selectAllNyOwner(ownerEmail: String): List<Site> {
        val id = userStorage.findByEmail(ownerEmail).id
        return siteStorage.findAllByOwnerId(id)
    }
}