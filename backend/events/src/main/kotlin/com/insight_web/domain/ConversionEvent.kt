package com.insight_web.domain

data class ConversionEvent(
    override val referrer: String,
    override val sessionId: String,
    override val url: String,
    val type: String, // "purchase", "signup" etc
    val value: Double? = null
) : TrackingEvent() {
    override val event: String = "conversion"
}