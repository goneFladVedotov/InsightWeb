package com.insightweb.auth.adapters.postgres.site

import com.insightweb.auth.domain.Site
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "site")
class SiteEntity {
    @Id
    var id: String? = null
    var ownerId: Long? = null
    var siteName: String? = null

    fun fillForSaving(site: Site): SiteEntity = apply {
        id = site.id
        ownerId = site.ownerId
        siteName = site.siteName
    }

    fun toDomainModel(): Site = Site(
        id = requireNotNull(id),
        ownerId = requireNotNull(ownerId),
        siteName = requireNotNull(siteName)
    )
}