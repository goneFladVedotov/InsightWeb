package com.insightweb.analytics.adapters.rest

import com.insightweb.analytics.adapters.rest.auth.AuthProcessor
import com.insightweb.analytics.usecase.AggregatedMetricsSelection
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.time.LocalDate

@RestController
@RequestMapping("/analytics/v1")
class AnalyticsController(
    private val aggregatedMetricsSelection: AggregatedMetricsSelection,
    private val authProcessor: AuthProcessor,
) {
    @GetMapping("metrics/fast")
    fun streamFastMetrics(
        @RequestHeader("Authorization") token: String,
        @RequestBody request: AnalyticsRequest
    ): SseEmitter {
        val puid = authProcessor.getPuid(token)
    }

    @GetMapping("metrics/slow")
    fun getSlowMetrics(
        @RequestHeader("Authorization") token: String,
        @RequestBody request: SlowMetricsRequest
    ):AggregatedMetricsDto {
        val puid = authProcessor.getPuid(token)
        return aggregatedMetricsSelection.select(puid, request.url, request.periodStart, request.periodEnd)
    }

    data class AnalyticsRequest(val url: String)
}