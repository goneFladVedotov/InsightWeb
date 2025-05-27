package com.insightweb.analytics.usecase

import com.insightweb.analytics.adapters.rest.AggregatedMetricsDto
import com.insightweb.analytics.adapters.rest.BrowserOsStat
import com.insightweb.analytics.adapters.rest.CountrySessions
import com.insightweb.analytics.adapters.rest.DateValue
import com.insightweb.analytics.adapters.rest.ElementClicks
import com.insightweb.analytics.adapters.rest.ErrorStat
import com.insightweb.analytics.adapters.rest.ScreenResolution
import com.insightweb.analytics.usecase.repository.AggregatedMetricsRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class AggregatedMetricsSelection(
    private val aggregatedMetricsRepository: AggregatedMetricsRepository
) {
    fun select(puid: Long, url: String, periodStart: LocalDate, periodEnd: LocalDate): AggregatedMetricsDto =
        AggregatedMetricsDto(
            avgSessionDurations = aggregatedMetricsRepository.getAvgSessionDuration(url, periodStart, periodEnd).map { DateValue(it.key, it.value) },
                    topCountries = aggregatedMetricsRepository.getTopCountries(url).map { CountrySessions(it.first, it.second) },
                    screenResolutions = aggregatedMetricsRepository.getScreenResolutions(url).map { ScreenResolution(it.first, it.second) },
                    browserOsStats = aggregatedMetricsRepository.getBrowserOsStats(url).map { BrowserOsStat(it.first, it.second, it.third) },
                    monthlyRevenue = aggregatedMetricsRepository.getMonthlyRevenue(url, 2025).map { DateValue(it.key, it.value) },
                    conversionTrends = aggregatedMetricsRepository.getConversionTrends(url),
                    avgSearchResults = aggregatedMetricsRepository.getAvgSearchResults(url),
                    deepScrollPercentage = aggregatedMetricsRepository.getDeepScrollPercentage(url),
                    topClickableElements = aggregatedMetricsRepository.getTopClickableElements(url, periodStart).map { ElementClicks(it.first, it.second) },
                    errorStats = aggregatedMetricsRepository.getErrorStats(url, periodStart).map { ErrorStat(it.first, it.second) }
        )
}