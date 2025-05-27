package com.insightweb.analytics.service

import com.insightweb.analytics.usecase.repository.FastMetricsRepository
import io.lettuce.core.event.metrics.MetricEventPublisher
import org.springframework.stereotype.Service

@Service
class MetricsService(
    private val metricRepo: FastMetricsRepository,
    private val eventPublisher: MetricEventPublisher
) {
    fun getCurrentMetrics(url: String): Map<MetricType, Any> {
        return mapOf(
            MetricType.ACTIVE_SESSIONS to metricRepo.getActiveSessions(url),
            MetricType.CLICKS to metricRepo.getRecentClicks(url)
            // ... другие метрики
        )
    }

    fun handleUserClick(url: String, userId: String) {
        metricRepo.trackClick(url, userId)
        eventPublisher.publish(
            Metric(
                type = MetricType.CLICKS.name,
                url = url,
                value = metricRepo.getRecentClicks(url)
            )
        )
    }
}