package com.insightweb.datastreamingserver.usecase.handler.event

import com.insight_web.domain.ConversionEvent
import com.insightweb.datastreamingserver.usecase.TrackingEventScheduler
import com.insightweb.datastreamingserver.usecase.notification.NotificationService
import com.insightweb.datastreamingserver.usecase.storage.ClickHouseRepository
import com.insightweb.datastreamingserver.usecase.storage.RedisRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class ConversionEventHandler(
    private val trackingEventScheduler: TrackingEventScheduler,
    private val redisRepo: RedisRepository,
    private val notificationService: NotificationService
) : TrackingEventHandler<ConversionEvent>{
    override val supportedEventType: KClass<out ConversionEvent> = ConversionEvent::class

    @Transactional
    override fun handle(event: ConversionEvent) {
        // 1. СОХРАНЕНИЕ ДАННЫХ
        // Фиксируем факт конверсии в ClickHouse
        trackingEventScheduler.addToDeque(event)

        // 2. ОСНОВНЫЕ МЕТРИКИ КОНВЕРСИЙ
        // Общее количество конверсий
        redisRepo.increment("conversions:total")
        // Конверсии по типам (покупка, регистрация и т.д.)
        redisRepo.increment("conversions:${event.type}")

        // 3. ФИНАНСОВЫЕ МЕТРИКИ
        // Если конверсия имеет денежное выражение (например, покупка)
        event.value?.let { amount ->
            // Общий доход
            redisRepo.increment("revenue:total", amount)
            // Доход по типам конверсий
            redisRepo.increment("revenue:${event.type}", amount)
        }

        // 4. УВЕДОМЛЕНИЯ
        // Отправляем уведомление о значимых покупках
        if (event.type == "purchase" && event.value ?: 0.0 > 1000) {
            notificationService.send("huy", event)
        }
    }
}