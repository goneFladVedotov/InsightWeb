package com.insightweb.streaming.usecase.session

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class SessionHandleEventListener(
    private val sessionTrackingService: SessionTrackingService
) {

    @EventListener(SessionHandleEvent::class)
    fun listen(event: SessionHandleEvent) {
        sessionTrackingService.updateEventCount(event.sessionId)
    }
}