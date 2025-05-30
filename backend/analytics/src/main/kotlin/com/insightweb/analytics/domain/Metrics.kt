package com.insightweb.analytics.domain

data class Metric(
    val type: String,
    val url: String,
    val value: Any,
    val timestamp: Long = System.currentTimeMillis()
)