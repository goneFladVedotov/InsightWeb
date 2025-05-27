package com.insightweb.analytics.adapters.rest

import java.time.LocalDate

data class SlowMetricsRequest(
    val url: String,
    val periodStart: LocalDate,
    val periodEnd: LocalDate,
)