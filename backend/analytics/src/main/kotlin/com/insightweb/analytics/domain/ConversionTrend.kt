package com.insightweb.analytics.domain

import java.time.LocalDate
data class ConversionTrend(
    val date: LocalDate,
    val type: String,
    val count: Int
)