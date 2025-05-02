package com.insightweb.streaming.usecase.storage

import com.insightweb.domain.TrackingEvent

interface ClickHouseRepository {
    fun insertBatch(events: List<TrackingEvent>)
}