package com.insight_web.data_ingestion_server.usecase

import com.insight_web.data_ingestion_server.usecase.analyticsIntegration.AnalyticsClient
import com.insight_web.data_ingestion_server.usecase.queueSender.QueueSender
import com.insight_web.domain.TrackingEvent
import org.springframework.stereotype.Service

@Service
class TrackingEventProcessing(
    private val queueSender: QueueSender,
) {
    fun process(event: TrackingEvent) {
        queueSender.send(event)
    }

}