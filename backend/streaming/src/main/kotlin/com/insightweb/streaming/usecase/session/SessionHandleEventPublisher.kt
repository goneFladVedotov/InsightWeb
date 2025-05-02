package com.insightweb.streaming.usecase.session

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class SessionHandleEventPublisher(
    private val eventPublisher: ApplicationEventPublisher
) {
    fun publish(sessionId: String) {
        eventPublisher.publishEvent(SessionHandleEvent(sessionId))
    }
}