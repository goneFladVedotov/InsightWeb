package com.insight_web.data_ingestion_server.adapters.rest.controller;


import com.insight_web.data_ingestion_server.domain.TrackerEvent;
import com.insight_web.data_ingestion_server.usecase.TrackerEventProcessing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event/collection/")
public class EventCollectionControllerController {

    private final TrackerEventProcessing eventService;

    public EventCollectionControllerController(TrackerEventProcessing eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<String> collectEvent(@RequestBody TrackerEvent event) {
        // Вызываем сервис
        eventService.processEvent(event);
        return ResponseEntity.status(HttpStatus.OK).body("Event received");
    }


}
