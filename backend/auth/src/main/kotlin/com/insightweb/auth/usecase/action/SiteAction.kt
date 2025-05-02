package com.insightweb.auth.usecase.action

import com.insightweb.auth.domain.Site

data class SiteAction(
    val siteName: String,
    val url: String,
) {
    fun toSite(userId: Long): Site = Site(
        id = url,
        ownerId = userId,
        siteName = siteName
    )
}
