package com.insight_web.domain

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
    val metadata: Map<String, String> = emptyMap()

    companion object {
        fun createTimestamp() = Instant.now().toEpochMilli()
    }
}