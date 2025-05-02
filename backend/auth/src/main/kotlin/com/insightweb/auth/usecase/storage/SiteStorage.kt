package com.insightweb.auth.usecase.storage

import com.insightweb.auth.domain.Site

interface SiteStorage {
    fun findById(siteId: String): Site
    fun findAllByOwnerId(ownerId: Long): List<Site>
    fun create(site: Site): Site
}