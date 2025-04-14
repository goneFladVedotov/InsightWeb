package com.insightweb.datastreamingserver.adapters.kafka.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.insight_web.domain.TrackingEvent
import com.insightweb.datastreamingserver.usecase.TrackingEventProcessing
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class TrackingEventListener(
    private val trackingEventProcessing: TrackingEventProcessing,
    private val objectMapper: ObjectMapper,
) {

    @KafkaListener(topics = ["insightweb-events"])
    fun processEvent(message: String) {
        val event = objectMapper.readValue<TrackingEvent>(message)
        trackingEventProcessing.process(event);
    }
}