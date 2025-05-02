package com.insightweb.streaming.usecase.session

import com.insightweb.domain.SessionEndEvent
import com.insightweb.domain.SessionStartEvent
import com.insightweb.streaming.usecase.storage.ClickHouseRepository
import org.springframework.stereotype.Service

@Service
class SessionTrackingService(
    private val eventRepository: ClickHouseRepository
) {
    private val sessionCache: MutableMap<String, Session> = HashMap()
    fun startSession(sessionStartEvent: SessionStartEvent) {
        if (!sessionCache.contains(sessionStartEvent.sessionId)) {
            sessionCache[sessionStartEvent.sessionId] = Session(sessionStartEvent.sessionId, sessionStartEvent.timestamp)
            eventRepository.insertBatch(listOf(sessionStartEvent))
        }
    }

    fun updateEventCount(sessionId: String) {
        sessionCache[sessionId]?.increment() ?: throw RuntimeException("session not found")
    }

    fun endSession(sessionEndEvent: SessionEndEvent): Session {
        return sessionCache[sessionEndEvent.sessionId] ?: throw RuntimeException("Session do not start").also {
            eventRepository.insertBatch(listOf(sessionEndEvent))
        }
    }
}