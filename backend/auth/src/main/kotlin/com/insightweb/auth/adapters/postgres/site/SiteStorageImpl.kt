package com.insightweb.auth.adapters.postgres.site

import com.insightweb.auth.domain.Site
import com.insightweb.auth.domain.exception.ResourceNotFoundException
import com.insightweb.auth.usecase.storage.SiteStorage
import org.springframework.stereotype.Repository
import kotlin.jvm.optionals.getOrNull

@Repository
class SiteStorageImpl(
    private val siteRepository: SiteRepository,
): SiteStorage {
    override fun findById(siteId: String): Site = siteRepository.findById(siteId).getOrNull()?.toDomainModel()
        ?: throw ResourceNotFoundException("site by id $siteId not found")

    override fun findAllByOwnerId(ownerId: Long): List<Site> = siteRepository.findAllByOwnerId(ownerId).map { it.toDomainModel() }
    override fun create(site: Site): Site = siteRepository.save(SiteEntity().fillForSaving(site)).toDomainModel()
}