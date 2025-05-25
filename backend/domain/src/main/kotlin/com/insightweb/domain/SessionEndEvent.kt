package com.insightweb.domain
data class SessionEndEvent(
    override val url: String,
    override val referrer: String,
    override val sessionId: String,
    val durationMs: Long,
    override val ip: String,
    override val deviceType: DeviceType,
    override val browser: String,
    override val os: String,
    override val language: String
) : TrackingEvent() {
    override val event: String = "SESSION_END"

}