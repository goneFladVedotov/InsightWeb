package com.insightweb.datastreamingserver.usecase.handler.metrics

import com.insight_web.domain.TrackingEvent

interface MetricsHandler {
    fun handle(event: TrackingEvent)
}