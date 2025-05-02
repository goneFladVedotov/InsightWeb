package com.insightweb.domain
data class ClickEvent(
    override val referrer: String,
    override val sessionId: String,
    override val url: String,
    val elementId: String,
    val elementText: String? = null
) : TrackingEvent() {
    override val event: String = "CLICK"
}