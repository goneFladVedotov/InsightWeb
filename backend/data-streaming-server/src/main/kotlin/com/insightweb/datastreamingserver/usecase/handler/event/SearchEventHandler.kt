package com.insightweb.datastreamingserver.usecase.handler.event

import com.insight_web.domain.SearchEvent
import com.insightweb.datastreamingserver.usecase.TrackingEventScheduler
import com.insightweb.datastreamingserver.usecase.storage.ClickHouseRepository
import com.insightweb.datastreamingserver.usecase.storage.RedisRepository
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class SearchEventHandler(
    private val trackingEventScheduler: TrackingEventScheduler,
    private val redisRepo: RedisRepository,
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
    }
}