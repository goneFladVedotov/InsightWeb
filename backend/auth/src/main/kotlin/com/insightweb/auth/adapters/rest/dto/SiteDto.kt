package com.insightweb.auth.adapters.rest.dto

import com.insightweb.auth.domain.Site

data class SiteDto(
    val name: String,
    val url: String,
)

fun Site.toSiteDto(): SiteDto = SiteDto(
    name = siteName,
    url = id
)
