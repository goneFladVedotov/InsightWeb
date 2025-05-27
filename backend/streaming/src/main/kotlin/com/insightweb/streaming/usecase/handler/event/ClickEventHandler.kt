package com.insightweb.streaming.usecase.handler.event

import com.insightweb.domain.ClickEvent
import com.insightweb.streaming.usecase.TrackingEventScheduler
import com.insightweb.streaming.usecase.repository.FastMetricsRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class ClickEventHandler(
    private val trackingEventScheduler: TrackingEventScheduler,
    private val fastMetricsRepository: FastMetricsRepository,
    ): TrackingEventHandler<ClickEvent> {
    override val supportedEventType: KClass<out ClickEvent> = ClickEvent::class

    @Transactional
    override fun handle(event: ClickEvent) {
        // 1. СОХРАНЕНИЕ ДАННЫХ
        // Сохраняем информацию о клике в ClickHouse
        trackingEventScheduler.addToDeque(event)

        fastMetricsRepository.trackClick(event.url, event.elementId)
    }
}