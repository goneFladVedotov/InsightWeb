package com.insightweb.domain

import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.time.Instant
import java.util.Collections.emptyMap

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "event")
sealed class TrackingEvent {
    abstract val event: String
    val timestamp: Instant = Instant.now()
    abstract val sessionId: String
    abstract val url: String
    abstract val referrer: String
    abstract val ip: String
    abstract val deviceType: DeviceType
    abstract val browser: String
    abstract val os: String
    abstract val language: String

    companion object {
        fun createTimestamp() = Instant.now().toEpochMilli()
    }
}

enum class DeviceType {
    MOBILE,
    DESKTOP,
    TABLET
}