package com.insight_web.data_ingestion_server.service;

import com.insight_web.data_ingestion_server.model.InsightEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventProcessingService {

    private static final String KAFKA_TOPIC = "insightweb-events";

    private final KafkaTemplate<String, InsightEvent> kafkaTemplate;

    public EventProcessingService(KafkaTemplate<String, InsightEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void processEvent(InsightEvent event) {
        // Пример валидации
        if (event.getSiteId() == null || event.getEventType() == null) {
            // Можно выбросить исключение, записать в логи, etc.
            System.err.println("Invalid event: missing siteId or eventType");
            return;
        }
        // Отправка события в Kafka
        String key = event.getSiteId(); // или userId
        kafkaTemplate.send(KAFKA_TOPIC, key, event);

        System.out.println("Event sent to Kafka: " + event);
    }
}

