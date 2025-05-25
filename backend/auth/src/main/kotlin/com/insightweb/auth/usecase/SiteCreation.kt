package com.insightweb.auth.usecase

import com.insightweb.auth.domain.Site
import com.insightweb.auth.usecase.action.SiteAction
import com.insightweb.auth.usecase.storage.SiteStorage
import com.insightweb.auth.usecase.storage.UserStorage
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class SiteCreation(
    private val userStorage: UserStorage,
    private val siteStorage: SiteStorage,
) {
    @Transactional
    fun create(
        siteAction: SiteAction,
        userEmail: String
    ): Site {
        val userId: Long = userStorage.findByEmail(userEmail).id!!
        val site: Site = siteAction.toSite(userId)
        return siteStorage.create(site)
    }
}