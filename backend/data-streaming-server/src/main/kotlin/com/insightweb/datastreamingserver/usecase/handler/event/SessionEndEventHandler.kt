package com.insightweb.datastreamingserver.usecase.handler.event

import com.insight_web.domain.SessionEndEvent
import com.insightweb.datastreamingserver.usecase.TrackingEventScheduler
import com.insightweb.datastreamingserver.usecase.session.SessionTrackingService
import com.insightweb.datastreamingserver.usecase.storage.ClickHouseRepository
import com.insightweb.datastreamingserver.usecase.storage.RedisRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class SessionEndEventHandler(
    private val trackingEventScheduler: TrackingEventScheduler,
    private val redisRepo: RedisRepository,
    private val sessionService: SessionTrackingService
) : TrackingEventHandler<SessionEndEvent>{
    override val supportedEventType: KClass<out SessionEndEvent> = SessionEndEvent::class

    @Transactional
    override fun handle(event: SessionEndEvent) {
        // 1. ЗАВЕРШЕНИЕ СЕССИИ
        // Получаем полные данные о сессии из сервиса
        val session = sessionService.endSession(event)

        trackingEventScheduler.addToDeque(event)

        // 2. МЕТРИКИ ДЛИТЕЛЬНОСТИ
        // Записываем длительность сессии для последующего анализа
        redisRepo.recordValue("session_duration", session.duration(event.timestamp))

        // 3. КОЭФФИЦИЕНТ ОТКАЗОВ
        // Если в сессии было только одно событие - считаем отказом
        if (session.eventCount == 1) {
            redisRepo.increment("bounce_rate:count")
        }

        // 4. ОБНОВЛЕНИЕ АКТИВНЫХ ПОЛЬЗОВАТЕЛЕЙ
        // Уменьшаем счетчик активных пользователей
        redisRepo.decrement("active_users:current")
        // Удаляем сессию из набора активных
        redisRepo.removeFromSet("active_sessions", event.sessionId)
    }

}