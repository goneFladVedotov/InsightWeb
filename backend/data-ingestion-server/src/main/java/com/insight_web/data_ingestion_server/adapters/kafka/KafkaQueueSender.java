package com.insight_web.data_ingestion_server.adapters.kafka;

import com.insight_web.data_ingestion_server.domain.TrackerEvent;
import com.insight_web.data_ingestion_server.usecase.TrackerEventProcessing;
import com.insight_web.data_ingestion_server.usecase.queueSender.QueueSender;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaQueueSender implements QueueSender {
    private static final String KAFKA_TOPIC = "insightweb-events";

    private final KafkaTemplate<String, TrackerEvent> kafkaTemplate;

    public KafkaQueueSender(KafkaTemplate<String, TrackerEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(TrackerEvent event) {
        String key = event.getSiteId();
        kafkaTemplate.send(KAFKA_TOPIC, key, event);
    }
}
