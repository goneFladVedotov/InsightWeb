package com.insightweb.datastreamingserver.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insight_web.domain.TrackerEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.stereotype.Service;
import org.apache.kafka.streams.StreamsBuilder;

import java.sql.*;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class TrackerEventProcessing {
    private final Properties properties;
    private final StreamsBuilder streamsBuilder;
    private final TrackerEventStorage trackerEventStorage;

    public void startStream() {
        // Чтение данных из Kafka
        KStream<String, String> stream = streamsBuilder.stream("insightweb-events", Consumed.with(Serdes.String(), Serdes.String()));

        // Обработка данных
        stream.mapValues(value -> {
            // Десериализация JSON в объект Event
            TrackerEvent event = parseJsonToEvent(value);

            // Здесь можно обработать или агрегировать событие
            processEvent(event);

            // Возвращаем обработанное событие
            return event;
        }).foreach((key, value) -> {
           trackerEventStorage.save(value);
        });

        // Запуск Kafka Streams
        KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), properties);
        streams.start();
    }

    private TrackerEvent parseJsonToEvent(String json) {
        // Логика для десериализации JSON в объект Event
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, TrackerEvent.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void processEvent(TrackerEvent event) {
        // Логика обработки события, например, агрегация, фильтрация
        System.out.println("Processing event: " + event);
    }
}
