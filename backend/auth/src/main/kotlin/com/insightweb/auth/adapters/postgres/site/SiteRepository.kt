package com.insightweb.auth.adapters.postgres.site

import org.springframework.data.jpa.repository.JpaRepository

interface SiteRepository: JpaRepository<SiteEntity, String> {
    fun findAllByOwnerId(ownerId: Long): List<SiteEntity>
}