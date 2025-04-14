package com.insightweb.datastreamingserver.usecase.handler.event

import com.insight_web.domain.ClickEvent
import com.insightweb.datastreamingserver.usecase.TrackingEventScheduler
import com.insightweb.datastreamingserver.usecase.storage.ClickHouseRepository
import com.insightweb.datastreamingserver.usecase.storage.RedisRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class ClickEventHandler(
    private val trackingEventScheduler: TrackingEventScheduler,
    private val redisRepo: RedisRepository,
): TrackingEventHandler<ClickEvent> {
    override val supportedEventType: KClass<out ClickEvent> = ClickEvent::class

    @Transactional
    override fun handle(event: ClickEvent) {
        // 1. СОХРАНЕНИЕ ДАННЫХ
        // Сохраняем информацию о клике в ClickHouse
        trackingEventScheduler.addToDeque(event)

        // 2. ОСНОВНЫЕ МЕТРИКИ КЛИКОВ
        // Общее количество кликов
        redisRepo.increment("clicks:total")
        // Клики по конкретному элементу
        redisRepo.increment("clicks:${event.elementId}")

        // 3. РАСЧЕТ CTR (CLICK-THROUGH RATE)
        // Увеличиваем счетчик кликов для элемента (нужен для последующего расчета CTR)
        val elementKey = "ctr:${event.elementId}"
        redisRepo.increment(elementKey)
    }
}