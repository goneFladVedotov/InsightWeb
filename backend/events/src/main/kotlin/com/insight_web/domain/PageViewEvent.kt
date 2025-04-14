package com.insight_web.domain

data class PageViewEvent(
    override val sessionId: String,
    override val url: String,
    override val referrer: String,
    val title: String,
    val screenWidth: Int? = null,
    val userAgent: String? = null
) : TrackingEvent() {
    override val event: String = "PAGE_VIEW"
}
