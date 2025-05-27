package com.insightweb.streaming.usecase.handler.event

import com.insightweb.domain.ConversionEvent
import com.insightweb.streaming.usecase.TrackingEventScheduler
import com.insightweb.streaming.usecase.repository.FastMetricsRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class ConversionEventHandler(
    private val trackingEventScheduler: TrackingEventScheduler,
    private val fastMetricsRepository: FastMetricsRepository,
    ) : TrackingEventHandler<ConversionEvent> {
    override val supportedEventType: KClass<out ConversionEvent> = ConversionEvent::class

    @Transactional
    override fun handle(event: ConversionEvent) {
        // 1. СОХРАНЕНИЕ ДАННЫХ
        // Фиксируем факт конверсии в ClickHouse
        trackingEventScheduler.addToDeque(event)

        // Конверсии по типам (покупка, регистрация и т.д.)
        fastMetricsRepository.trackConversion(event.url, event.type, event.value!!)
    }
}