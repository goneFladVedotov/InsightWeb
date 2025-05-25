package com.insightweb.streaming.usecase.handler.event

import com.insightweb.domain.ErrorEvent
import com.insightweb.streaming.usecase.TrackingEventScheduler
import com.insightweb.streaming.usecase.repository.FastMetricsRepository
import com.insightweb.streaming.usecase.session.SessionHandleEventPublisher
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class ErrorEventHandler(
    private val trackingEventScheduler: TrackingEventScheduler,
    private val fastMetricsRepository: FastMetricsRepository,
    private val sessionHandleEventPublisher: SessionHandleEventPublisher,

    ): TrackingEventHandler<ErrorEvent> {
    override val supportedEventType: KClass<out ErrorEvent> = ErrorEvent::class

    override fun handle(event: ErrorEvent) {
        // 1. ЛОГИРОВАНИЕ ОШИБОК
        // Сохраняем информацию об ошибке для последующего анализа
        trackingEventScheduler.addToDeque(event)

        fastMetricsRepository.trackError(event.url, event.errorType, event.browser)

        sessionHandleEventPublisher.publish(event.sessionId)
    }
}