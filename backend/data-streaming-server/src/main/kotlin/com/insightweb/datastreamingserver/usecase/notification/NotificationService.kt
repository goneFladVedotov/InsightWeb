package com.insightweb.datastreamingserver.usecase.notification

import com.insight_web.domain.TrackingEvent
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class NotificationService(
    private val restTemplate: RestTemplate
) {
    fun send(message: String, event: TrackingEvent) {

    }
}