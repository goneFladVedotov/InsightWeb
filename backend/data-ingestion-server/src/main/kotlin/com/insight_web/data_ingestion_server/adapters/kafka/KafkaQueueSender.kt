package com.insight_web.data_ingestion_server.adapters.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.insight_web.data_ingestion_server.usecase.queueSender.QueueSender
import com.insight_web.domain.TrackingEvent
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaQueueSender(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper,
): QueueSender {
    private val KAFKA_TOPIC = "insightweb-events"

    override fun send(event: TrackingEvent) {
        kafkaTemplate.send(KAFKA_TOPIC, objectMapper.writeValueAsString(event))
    }
}