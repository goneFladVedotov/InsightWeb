package com.insightweb.streaming.usecase.handler.event

import com.insightweb.domain.SessionStartEvent
import com.insightweb.streaming.usecase.TrackingEventScheduler
import com.insightweb.streaming.usecase.repository.FastMetricsRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class SessionStartEventHandler(
    private val trackingEventScheduler: TrackingEventScheduler,
    private val fastMetricsRepository: FastMetricsRepository,
): TrackingEventHandler<SessionStartEvent> {
    override val supportedEventType: KClass<out SessionStartEvent> = SessionStartEvent::class

    @Transactional
    override fun handle(event: SessionStartEvent) {
        trackingEventScheduler.addToDeque(event)

        // 2. АКТИВНЫЕ ПОЛЬЗОВАТЕЛИ
        // Увеличиваем счетчик текущих активных пользователей
        fastMetricsRepository.incrementActiveSessions(event.url)
    }
}