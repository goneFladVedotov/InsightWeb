package com.insightweb.analytics.adapters.rest

import com.insightweb.analytics.domain.ConversionTrend
import java.time.LocalDate

data class AggregatedMetricsDto(
    val avgSessionDurations: List<DateValue>? = null,
    val topCountries: List<CountrySessions>? = null,
    val screenResolutions: List<ScreenResolution>? = null,
    val browserOsStats: List<BrowserOsStat>? = null,
    val monthlyRevenue: List<DateValue>? = null,
    val conversionTrends: List<ConversionTrend>? = null,
    val avgSearchResults: Double? = null,
    val deepScrollPercentage: Double? = null,
    val topClickableElements: List<ElementClicks>? = null,
    val errorStats: List<ErrorStat>? = null
)

data class DateValue(
    val date: LocalDate,
    val value: Double
)

data class CountrySessions(
    val country: String,
    val sessions: Int
)

data class ScreenResolution(
    val screenWidth: Int,
    val pageViews: Int
)

data class BrowserOsStat(
    val browser: String,
    val os: String,
    val sessions: Int
)

data class ElementClicks(
    val elementId: String,
    val clicks: Int
)

data class ErrorStat(
    val errorType: String,
    val errorCount: Int
)
