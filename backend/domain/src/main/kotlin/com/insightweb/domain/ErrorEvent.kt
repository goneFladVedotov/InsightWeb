package com.insightweb.domain

data class ErrorEvent(
    override val referrer: String,
    override val sessionId: String,
    override val url: String,
    val errorType: String, // "js_error", "http_404"
    val message: String? = null,
    override val ip: String,
    override val deviceType: DeviceType,
    override val browser: String,
    override val os: String,
    override val language: String
) : TrackingEvent() {
    override val event: String = "ERROR"
}