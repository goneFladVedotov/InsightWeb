package com.insightweb.streaming.usecase.handler.event

import com.insightweb.domain.ConversionEvent
import com.insightweb.streaming.usecase.TrackingEventScheduler
import com.insightweb.streaming.usecase.repository.FastMetricsRepository
import com.insightweb.streaming.usecase.session.SessionHandleEventPublisher
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class ConversionEventHandler(
    private val trackingEventScheduler: TrackingEventScheduler,
    private val fastMetricsRepository: FastMetricsRepository,
    private val sessionHandleEventPublisher: SessionHandleEventPublisher,
    ) : TrackingEventHandler<ConversionEvent> {
    override val supportedEventType: KClass<out ConversionEvent> = ConversionEvent::class

    @Transactional
    override fun handle(event: ConversionEvent) {
        // 1. СОХРАНЕНИЕ ДАННЫХ
        // Фиксируем факт конверсии в ClickHouse
        trackingEventScheduler.addToDeque(event)

        // Конверсии по типам (покупка, регистрация и т.д.)
        fastMetricsRepository.trackConversion(event.url, event.type, event.value!!)

        sessionHandleEventPublisher.publish(event.sessionId)
    }
}