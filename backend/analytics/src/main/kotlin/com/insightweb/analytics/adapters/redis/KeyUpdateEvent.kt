package com.insightweb.analytics.adapters.redis

import com.fasterxml.jackson.annotation.JsonFormat
import com.insightweb.analytics.domain.MetricType
import java.time.Instant

data class KeyUpdateEvent(
    val metricType: MetricType,
    val url: String,
    val data: Any?,
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    val timestamp: Instant = Instant.now()
)

