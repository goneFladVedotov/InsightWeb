package com.insightweb.datastreamingserver.usecase.session

import java.time.Duration
import java.time.Instant

data class Session(
    val startId: String,
    val timestamp: Instant
) {
    var eventCount = 0;

    fun increment() {
        eventCount++
    }
    fun duration(endTimestamp: Instant): Duration = Duration.between(timestamp, endTimestamp)
}
