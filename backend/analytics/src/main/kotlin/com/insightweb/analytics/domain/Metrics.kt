package com.insightweb.analytics.domain

data class Metric(
    val type: String,
    val url: String,
    val value: Any,
    val timestamp: Long = System.currentTimeMillis()
)

enum class MetricType {
    ACTIVE_SESSIONS,
    CLICKS,
    ERRORS,
    CONVERSIONS,
    SCROLL_DEPTH,
    SEARCH_QUERIES
}
