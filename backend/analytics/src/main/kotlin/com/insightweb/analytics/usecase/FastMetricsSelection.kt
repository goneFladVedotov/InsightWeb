package com.insightweb.analytics.usecase

import com.insightweb.analytics.adapters.redis.RedisSubscriptionService
import com.insightweb.analytics.domain.MetricType
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Service
class FastMetricsUpdateSubscription(
    private val redisSubscriptionService: RedisSubscriptionService,
) {
    fun subscribe(metricType: MetricType, url: String, emitter: SseEmitter) {
        redisSubscriptionService.subscribe(metricType, url, emitter)
    }

}