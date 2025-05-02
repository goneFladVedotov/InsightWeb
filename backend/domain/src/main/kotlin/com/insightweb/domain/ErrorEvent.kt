package com.insightweb.domain

data class ErrorEvent(
    override val referrer: String,
    override val sessionId: String,
    override val url: String,
    val errorType: String, // "js_error", "http_404"
    val message: String? = null
) : TrackingEvent() {
    override val event: String = "ERROR"
}