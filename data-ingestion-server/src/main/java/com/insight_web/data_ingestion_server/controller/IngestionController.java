package com.insight_web.data_ingestion_server.controller;


import com.insight_web.data_ingestion_server.model.InsightEvent;
import com.insight_web.data_ingestion_server.service.EventProcessingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collect")
public class IngestionController {

    private final EventProcessingService eventService;

    public IngestionController(EventProcessingService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<String> collectEvent(@RequestBody InsightEvent event) {
        // Вызываем сервис
        eventService.processEvent(event);
        return ResponseEntity.status(HttpStatus.OK).body("Event received");
    }
}
