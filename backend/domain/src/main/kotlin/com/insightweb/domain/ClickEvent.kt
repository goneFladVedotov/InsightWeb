package com.insightweb.domain
data class ClickEvent(
    override val referrer: String,
    override val sessionId: String,
    override val url: String,
    val elementId: String,
    val elementText: String? = null,
    override val ip: String,
    override val deviceType: DeviceType,
    override val browser: String,
    override val os: String,
    override val language: String
) : TrackingEvent() {
    override val event: String = "CLICK"
}