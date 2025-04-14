package com.insightweb.datastreamingserver.usecase.handler.event

import com.insight_web.domain.PageViewEvent
import com.insightweb.datastreamingserver.usecase.TrackingEventScheduler
import com.insightweb.datastreamingserver.usecase.storage.ClickHouseRepository
import com.insightweb.datastreamingserver.usecase.storage.RedisRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class PageViewEventHandler(
    private val trackingEventScheduler: TrackingEventScheduler
    private val redisRepo: RedisRepository,
): TrackingEventHandler<PageViewEvent> {
    override val supportedEventType: KClass<out PageViewEvent> = PageViewEvent::class

    @Transactional
    override fun handle(event: PageViewEvent) {
        // 1. СОХРАНЕНИЕ ДАННЫХ
        // Записываем сырое событие в ClickHouse для долгосрочного хранения
        trackingEventScheduler.addToDeque(event)

        // 2. ОСНОВНЫЕ МЕТРИКИ
        // Общее количество просмотров
        redisRepo.increment("pageviews:total")
        // Просмотры по конкретному URL
        redisRepo.increment("pageviews:${event.url}")
        // Уникальные посетители (используем Set для устранения дублей)
        redisRepo.addToSet("unique_visitors:daily", event.sessionId)

        // 4. АНАЛИЗ УСТРОЙСТВ
        // Определяем тип устройства по User-Agent и ведем статистику
        event.userAgent?.let {
            val deviceType = parseDeviceType(it)
            redisRepo.increment("devices:$deviceType")
        }

    }

    private fun parseDeviceType(userAgent: String): String {
        return when {
            userAgent.contains("Mobile") -> "mobile"
            userAgent.contains("Tablet") -> "tablet"
            else -> "desktop"
        }
    }
}