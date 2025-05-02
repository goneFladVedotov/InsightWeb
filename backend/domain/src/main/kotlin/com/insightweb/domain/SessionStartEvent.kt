package com.insightweb.domain
data class SessionStartEvent(
    override val url: String,
    override val referrer: String,
    override val sessionId: String,
    val ip: String,
    val userAgent: String
) : TrackingEvent() {
    override val event: String = "SESSION_START"

}