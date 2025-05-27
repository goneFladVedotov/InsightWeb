package com.insightweb.streaming.usecase.handler.event

import com.insightweb.domain.SessionEndEvent
import com.insightweb.streaming.usecase.TrackingEventScheduler
import com.insightweb.streaming.usecase.repository.FastMetricsRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class SessionEndEventHandler(
    private val trackingEventScheduler: TrackingEventScheduler,
    private val fastMetricsRepository: FastMetricsRepository,
) : TrackingEventHandler<SessionEndEvent> {
    override val supportedEventType: KClass<out SessionEndEvent> = SessionEndEvent::class

    @Transactional
    override fun handle(event: SessionEndEvent) {
        trackingEventScheduler.addToDeque(event)

        // 2. МЕТРИКИ ДЛИТЕЛЬНОСТИ
        // Записываем длительность сессии для последующего анализа
        fastMetricsRepository.decrementActiveSessions(event.url)
    }

}