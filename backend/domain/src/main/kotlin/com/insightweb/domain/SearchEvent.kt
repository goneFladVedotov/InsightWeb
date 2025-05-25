package com.insightweb.domain
data class SearchEvent(
    override val sessionId: String,
    override val referrer: String,
    override val url: String,
    val query: String,
    val resultsCount: Int? = null,
    override val ip: String,
    override val deviceType: DeviceType,
    override val browser: String,
    override val os: String,
    override val language: String
) : TrackingEvent() {
    override val event: String = "SEARCH"
}