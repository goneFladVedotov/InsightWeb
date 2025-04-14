package com.insightweb.datastreamingserver.usecase.session

import com.insight_web.domain.SessionEndEvent
import com.insight_web.domain.SessionStartEvent
import com.insightweb.datastreamingserver.usecase.storage.ClickHouseRepository
import org.springframework.stereotype.Service
@Service
class SessionTrackingService(
    private val eventRepository: ClickHouseRepository
) {
    private val sessionCache: MutableMap<String, Session> = HashMap()
    fun startSession(sessionStartEvent: SessionStartEvent) {
        if (!sessionCache.contains(sessionStartEvent.sessionId)) {
            sessionCache[sessionStartEvent.sessionId] = Session(sessionStartEvent.sessionId, sessionStartEvent.timestamp)
            eventRepository.insert(sessionStartEvent)
        }
    }

    fun updateEventCount(sessionId: String) {
        sessionCache[sessionId]?.increment() ?: throw RuntimeException("session not found")
    }

    fun endSession(sessionEndEvent: SessionEndEvent): Session {
        return sessionCache[sessionEndEvent.sessionId] ?: throw RuntimeException("Session do not start").also {
            eventRepository.insert(sessionEndEvent)
        }
    }
}