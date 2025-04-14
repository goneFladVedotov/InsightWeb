package com.insightweb.datastreamingserver.usecase.storage

import com.insight_web.domain.TrackingEvent

interface ClickHouseRepository {
    fun insertBatch(events: List<TrackingEvent>)
}