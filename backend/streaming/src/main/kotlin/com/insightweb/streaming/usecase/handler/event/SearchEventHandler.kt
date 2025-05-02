package com.insightweb.streaming.usecase.handler.event

import com.insightweb.domain.SearchEvent
import com.insightweb.streaming.usecase.TrackingEventScheduler
import com.insightweb.streaming.usecase.session.SessionHandleEventPublisher
import com.insightweb.streaming.usecase.storage.RedisRepository
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class SearchEventHandler(
    private val trackingEventScheduler: TrackingEventScheduler,
    private val redisRepo: RedisRepository,
    private val sessionHandleEventPublisher: SessionHandleEventPublisher,
    ): TrackingEventHandler<SearchEvent> {
    override val supportedEventType: KClass<out SearchEvent> = SearchEvent::class

    override fun handle(event: SearchEvent) {
        // 1. СОХРАНЕНИЕ ДАННЫХ
        // Записываем поисковый запрос в ClickHouse
        trackingEventScheduler.addToDeque(event)

        // 2. ПОИСКОВАЯ АНАЛИТИКА
        // Популярные запросы (топ-10)
        redisRepo.incrementSortedSet("search_popularity", event.query, 1.0)

        // 3. АНАЛИЗ НУЛЕВЫХ РЕЗУЛЬТАТОВ
        // Если нет результатов поиска - сигнализируем проблему
        if (event.resultsCount == 0) {
            redisRepo.increment("search_failures:${event.query}")
        }

        // 4. СЕССИОННЫЙ АНАЛИЗ
        // Связываем поисковый запрос с сессией пользователя
        redisRepo.addToSessionHistory(event.sessionId, "search:${event.query}")
        sessionHandleEventPublisher.publish(event.sessionId)
    }
}