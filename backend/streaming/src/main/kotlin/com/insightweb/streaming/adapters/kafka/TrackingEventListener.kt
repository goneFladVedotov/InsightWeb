package com.insightweb.streaming.adapters.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.insightweb.domain.TrackingEvent
import com.insightweb.streaming.usecase.TrackingEventProcessing
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