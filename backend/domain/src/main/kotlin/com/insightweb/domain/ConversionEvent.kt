package com.insightweb.domain

data class ConversionEvent(
    override val referrer: String,
    override val sessionId: String,
    override val url: String,
    val type: String, // "purchase", "signup" etc
    val value: Double? = null,
    override val ip: String,
    override val deviceType: DeviceType,
    override val browser: String,
    override val os: String,
    override val language: String
) : TrackingEvent() {
    override val event: String = "conversion"
}