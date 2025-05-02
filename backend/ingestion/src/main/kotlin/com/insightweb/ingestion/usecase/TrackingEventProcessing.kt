package com.insightweb.ingestion.usecase

import com.insightweb.ingestion.usecase.queueSender.QueueSender
import com.insightweb.domain.TrackingEvent
import com.insightweb.ingestion.usecase.analyticsIntegration.AuthClient
import org.springframework.stereotype.Service

@Service
class TrackingEventProcessing(
    private val queueSender: QueueSender,
    private val authClient: AuthClient,
) {
    fun process(event: TrackingEvent) {
        if (!authClient.isSiteExisted(event.url)) throw RuntimeException("Site does not exist")
        queueSender.send(event)
    }
}