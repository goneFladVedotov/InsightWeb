package com.insightweb.streaming.usecase.handler.metrics

import com.insightweb.domain.TrackingEvent

interface MetricsHandler {
    fun handle(event: TrackingEvent)
}