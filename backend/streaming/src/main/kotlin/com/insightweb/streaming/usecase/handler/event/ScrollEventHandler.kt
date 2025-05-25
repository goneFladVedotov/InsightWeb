package com.insightweb.streaming.usecase.handler.event

import com.insightweb.domain.ScrollEvent
import com.insightweb.streaming.usecase.TrackingEventScheduler
import com.insightweb.streaming.usecase.repository.FastMetricsRepository
import com.insightweb.streaming.usecase.session.SessionHandleEventPublisher
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class ScrollEventHandler(
    private val trackingEventScheduler: TrackingEventScheduler,
    private val fastMetricsRepository: FastMetricsRepository,
    private val sessionHandleEventPublisher: SessionHandleEventPublisher,
    ): TrackingEventHandler<ScrollEvent> {
    override val supportedEventType: KClass<out ScrollEvent> = ScrollEvent::class

    @Transactional
    override fun handle(event: ScrollEvent) {
        // 1. СОХРАНЕНИЕ ДАННЫХ
        // Фиксируем событие прокрутки в ClickHouse
        trackingEventScheduler.addToDeque(event)

        // 2. АНАЛИЗ ВОВЛЕЧЕННОСТИ
        // Записываем глубину прокрутки для анализа вовлеченности
        fastMetricsRepository.updateScrollDepth(event.url, event.sessionId, event.depth)

        sessionHandleEventPublisher.publish(event.sessionId)

    }
}