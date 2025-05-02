package com.insightweb.domain
data class SessionEndEvent(
    override val url: String,
    override val referrer: String,
    override val sessionId: String,
    val durationMs: Long
) : TrackingEvent() {
    override val event: String = "SESSION_END"

}