package com.insight_web.data_ingestion_server.usecase.queueSender

import com.insight_web.domain.TrackingEvent

interface QueueSender {
    fun send(event: TrackingEvent)
}