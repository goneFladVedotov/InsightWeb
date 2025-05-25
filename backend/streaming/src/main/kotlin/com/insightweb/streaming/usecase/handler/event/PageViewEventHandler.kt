package com.insightweb.streaming.usecase.handler.event

import com.insightweb.domain.PageViewEvent
import com.insightweb.streaming.usecase.TrackingEventScheduler
import com.insightweb.streaming.usecase.repository.FastMetricsRepository
import com.insightweb.streaming.usecase.session.SessionHandleEventPublisher
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class PageViewEventHandler(
    private val trackingEventScheduler: TrackingEventScheduler,
    private val sessionHandleEventPublisher: SessionHandleEventPublisher,

    ): TrackingEventHandler<PageViewEvent> {
    override val supportedEventType: KClass<out PageViewEvent> = PageViewEvent::class

    @Transactional
    override fun handle(event: PageViewEvent) {
        // 1. СОХРАНЕНИЕ ДАННЫХ
        // Записываем сырое событие в ClickHouse для долгосрочного хранения
        trackingEventScheduler.addToDeque(event)

        sessionHandleEventPublisher.publish(event.sessionId)

    }
}