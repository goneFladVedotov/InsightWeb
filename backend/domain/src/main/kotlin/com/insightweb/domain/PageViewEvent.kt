package com.insightweb.domain

data class PageViewEvent(
    override val sessionId: String,
    override val url: String,
    override val referrer: String,
    val title: String,
    val screenWidth: Int? = null,
    override val ip: String,
    override val deviceType: DeviceType,
    override val browser: String,
    override val os: String,
    override val language: String
) : TrackingEvent() {
    override val event: String = "PAGE_VIEW"
}
