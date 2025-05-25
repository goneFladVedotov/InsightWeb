package com.insightweb.domain

data class ScrollEvent(
    override val sessionId: String,
    override val referrer: String,
    override val url: String,
    val depth: Int, // 25, 50, 75, 100
    override val ip: String,
    override val deviceType: DeviceType,
    override val browser: String,
    override val os: String,
    override val language: String
) : TrackingEvent() {
    override val event: String = "SCROLL"
}