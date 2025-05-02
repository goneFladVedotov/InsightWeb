package com.insightweb.ingestion.adapters.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.insightweb.ingestion.usecase.queueSender.QueueSender
import com.insightweb.domain.TrackingEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaQueueSender(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper,
    @Value("\${insightweb.kafka.topic}")
    private val topicName: String
): QueueSender {
    override fun send(event: TrackingEvent) {
        kafkaTemplate.send(topicName, objectMapper.writeValueAsString(event))
    }
}