package com.insightweb.streaming.usecase.handler.event

import com.insightweb.domain.SearchEvent
import com.insightweb.streaming.usecase.TrackingEventScheduler
import com.insightweb.streaming.usecase.repository.FastMetricsRepository
import com.insightweb.streaming.usecase.session.SessionHandleEventPublisher
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class SearchEventHandler(
    private val trackingEventScheduler: TrackingEventScheduler,
    private val fastMetricsRepository: FastMetricsRepository,
    private val sessionHandleEventPublisher: SessionHandleEventPublisher,
    ): TrackingEventHandler<SearchEvent> {
    override val supportedEventType: KClass<out SearchEvent> = SearchEvent::class

    override fun handle(event: SearchEvent) {
        // 1. СОХРАНЕНИЕ ДАННЫХ
        // Записываем поисковый запрос в ClickHouse
        trackingEventScheduler.addToDeque(event)

        // 2. ПОИСКОВАЯ АНАЛИТИКА
        // Популярные запросы (топ-10)
        fastMetricsRepository.trackSearch(event.url, event.query)

        sessionHandleEventPublisher.publish(event.sessionId)
    }
}