package com.insightweb.ingestion.adapters.rest.controller

import com.insightweb.ingestion.usecase.TrackingEventProcessing
import com.insightweb.domain.TrackingEvent
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/event/collect")
class EventCollectionController(
    private val trackingEventProcessing: TrackingEventProcessing,
) {
    @PostMapping
    fun collectEvent(@RequestBody event: TrackingEvent): ResponseEntity<String> {
        trackingEventProcessing.process(event)
        return ResponseEntity.status(HttpStatus.OK).body("Event received")
    }

}