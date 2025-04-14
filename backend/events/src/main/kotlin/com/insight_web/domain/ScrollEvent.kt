package com.insight_web.domain

data class ScrollEvent(
    override val sessionId: String,
    override val referrer: String,
    override val url: String,
    val depth: Int // 25, 50, 75, 100
) : TrackingEvent() {
    override val event: String = "SCROLL"
}