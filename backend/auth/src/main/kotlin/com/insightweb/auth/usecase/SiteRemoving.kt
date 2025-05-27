package com.insightweb.auth.usecase

import com.insightweb.auth.usecase.storage.SiteStorage
import jakarta.transaction.Transactional

class SiteRemoving(
    private val siteStorage: SiteStorage,
) {
    @Transactional
    fun remove(siteId: String) {
        siteStorage.deleteById(siteId)
    }
}