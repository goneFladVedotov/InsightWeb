package com.insightweb.streaming.adapters.redis

import com.insightweb.domain.DeviceType
import com.insightweb.streaming.usecase.repository.FastMetricsRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.concurrent.TimeUnit

@Repository
class FastMetricsRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, Any>
): FastMetricsRepository {
    // Активные сессии
    override fun incrementActiveSessions(url: String) {
        redisTemplate.opsForValue().increment("site:$url:active_sessions")
    }

    override fun decrementActiveSessions(url: String) {
        redisTemplate.opsForValue().decrement("site:$url:active_sessions")
    }

    // Клики
    override fun trackClick(url: String, elementId: String, ttlMinutes: Long) {
        val key = "site:$url:clicks:${Instant.now().epochSecond / 60}"
        redisTemplate.opsForHyperLogLog().add(key, elementId)
        redisTemplate.expire(key, ttlMinutes, TimeUnit.MINUTES)
    }

    // Ошибки
    override fun trackError(url: String, errorType: String, browser: String, ttlMinutes: Long) {
        val errorKey = "site:$url:errors:$browser"
        redisTemplate.opsForValue().increment(errorKey)
        redisTemplate.expire(errorKey, ttlMinutes, TimeUnit.MINUTES)
    }

    // Поисковые запросы
    override fun trackSearch(url: String, query: String, ttlMinutes: Long) {
        val key = "site:$url:searches"
        redisTemplate.opsForZSet().incrementScore(key, query, 1.0)
        redisTemplate.expire(key, ttlMinutes, TimeUnit.MINUTES)
    }

    // Конверсии
    override fun trackConversion(url: String, type: String, value: Double?, ttlHours: Long) {
        val key = "site:$url:conversions:$type"
        value?.let {
            redisTemplate.opsForValue().increment(key, it)
        } ?: redisTemplate.opsForValue().increment(key)
        redisTemplate.expire(key, ttlHours, TimeUnit.HOURS)
    }

    // Глубина прокрутки
    override fun updateScrollDepth(url: String, sessionId: String, depth: Int) {
        redisTemplate.opsForHash<String, Int>().put(
            "site:$url:scroll_depth",
            sessionId,
            depth.coerceAtMost(100)
        )
    }

    // География
    override fun updateGeo(url: String, country: String, city: String) {
        redisTemplate.opsForZSet().incrementScore("site:$url:geo:countries", country, 1.0)
        redisTemplate.opsForZSet().incrementScore("site:$url:geo:cities", city, 1.0)
    }

    // Устройства и языки
    override fun trackDevice(url: String, deviceType: DeviceType) {
        redisTemplate.opsForHash<String, Int>().increment("site:$url:devices", deviceType.name, 1)
    }

    override fun trackLanguage(url: String, language: String) {
        redisTemplate.opsForHash<String, Int>().increment("site:$url:languages", language, 1)
    }
}