package com.insightweb.streaming.usecase.handler.event

import com.insightweb.domain.ScrollEvent
import com.insightweb.streaming.usecase.TrackingEventScheduler
import com.insightweb.streaming.usecase.session.SessionHandleEventPublisher
import com.insightweb.streaming.usecase.storage.RedisRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class ScrollEventHandler(
    private val trackingEventScheduler: TrackingEventScheduler,
    private val redisRepo: RedisRepository,
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
        redisRepo.recordValue("scroll_depth:${event.url}", event.depth)

        // 3. СЕГМЕНТАЦИЯ ПО ГЛУБИНЕ
        // Учет воронки вовлечения (25%, 50%, 75%, 100%)
        when (event.depth) {
            in 1..24 -> redisRepo.increment("engagement:low:${event.url}")
            in 25..49 -> redisRepo.increment("engagement:medium:${event.url}")
            in 50..74 -> redisRepo.increment("engagement:high:${event.url}")
            else -> redisRepo.increment("engagement:full:${event.url}")
        }
        sessionHandleEventPublisher.publish(event.sessionId)

    }
}