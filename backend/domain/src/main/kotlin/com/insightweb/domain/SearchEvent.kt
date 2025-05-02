package com.insightweb.domain
data class SearchEvent(
    override val sessionId: String,
    override val referrer: String,
    override val url: String,
    val query: String,
    val resultsCount: Int? = null
) : TrackingEvent() {
    override val event: String = "SEARCH"
}