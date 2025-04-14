package com.insightweb.datastreamingserver.usecase.handler.event

import com.insight_web.domain.SessionStartEvent
import com.insightweb.datastreamingserver.usecase.TrackingEventScheduler
import com.insightweb.datastreamingserver.usecase.session.SessionTrackingService
import com.insightweb.datastreamingserver.usecase.storage.ClickHouseRepository
import com.insightweb.datastreamingserver.usecase.storage.RedisRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class SessionStartEventHandler(
    private val trackingEventScheduler: TrackingEventScheduler,
    private val redisRepo: RedisRepository,
    private val sessionService: SessionTrackingService
): TrackingEventHandler<SessionStartEvent> {
    override val supportedEventType: KClass<out SessionStartEvent> = SessionStartEvent::class

    @Transactional
    override fun handle(event: SessionStartEvent) {
        trackingEventScheduler.addToDeque(event)

        // 1. ИНИЦИАЛИЗАЦИЯ СЕССИИ
        // Регистрируем новую сессию в сервисе сессий
        sessionService.startSession(event)

        // 2. АКТИВНЫЕ ПОЛЬЗОВАТЕЛИ
        // Увеличиваем счетчик текущих активных пользователей
        redisRepo.increment("active_users:current")
        // Добавляем сессию в набор активных сессий
        redisRepo.addToSet("active_sessions", event.sessionId)
    }
}