package com.insightweb.domain
data class SessionStartEvent(
    override val url: String,
    override val referrer: String,
    override val sessionId: String,
    override val deviceType: DeviceType,
    override val browser: String,
    override val os: String,
    override val language: String,
    override val ip: String
) : TrackingEvent() {
    override val event: String = "SESSION_START"

}