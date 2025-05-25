package com.insightweb.streaming.usecase.repository

import com.insightweb.domain.TrackingEvent

interface AggregatedMetricsRepository {
    fun insertEvents(events: List<TrackingEvent>)

}