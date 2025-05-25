package com.insightweb.streaming.usecase.handler.event

import com.insightweb.domain.SessionEndEvent
import com.insightweb.streaming.usecase.TrackingEventScheduler
import com.insightweb.streaming.usecase.repository.FastMetricsRepository
import com.insightweb.streaming.usecase.session.SessionTrackingService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class SessionEndEventHandler(
    private val trackingEventScheduler: TrackingEventScheduler,
    private val fastMetricsRepository: FastMetricsRepository,
    private val sessionService: SessionTrackingService
) : TrackingEventHandler<SessionEndEvent> {
    override val supportedEventType: KClass<out SessionEndEvent> = SessionEndEvent::class

    @Transactional
    override fun handle(event: SessionEndEvent) {
        // 1. ЗАВЕРШЕНИЕ СЕССИИ
        // Получаем полные данные о сессии из сервиса
        val session = sessionService.endSession(event)

        trackingEventScheduler.addToDeque(event)

        // 2. МЕТРИКИ ДЛИТЕЛЬНОСТИ
        // Записываем длительность сессии для последующего анализа
        fastMetricsRepository.decrementActiveSessions(event.url)

    }

}