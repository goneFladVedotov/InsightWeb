package com.insightweb.ingestion.usecase.queueSender

import com.insightweb.domain.TrackingEvent

interface QueueSender {
    fun send(event: TrackingEvent)
}