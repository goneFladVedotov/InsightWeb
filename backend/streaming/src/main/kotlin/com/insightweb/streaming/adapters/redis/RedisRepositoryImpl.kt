package com.insightweb.streaming.adapters.redis

import com.insightweb.streaming.usecase.storage.RedisRepository
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class RedisRepositoryImpl(
    private val redisTemplate: StringRedisTemplate
): RedisRepository {
    override fun increment(key: String) {
        // Увеличиваем значение по ключу на 1
        redisTemplate.opsForValue().increment(key, 1)
    }

    override fun increment(key: String, amount: Double) {
        // Увеличиваем значение по ключу на заданное количество
        redisTemplate.opsForValue().increment(key, amount.toLong())
    }

    override fun addToSet(key: String, param: String) {
        // Добавляем элемент в множество (set)
        redisTemplate.opsForSet().add(key, param)
    }

    // 4. ОБНОВЛЕНИЕ АКТИВНЫХ ПОЛЬЗОВАТЕЛЕЙ
    // Уменьшаем счетчик активных пользователей
    override fun decrement(key: String) {
        redisTemplate.opsForValue().decrement(key, 1)
    }

    // Удаляем сессию из набора активных
    override fun removeFromSet(key: String, sessionId: String) {
        redisTemplate.opsForSet().remove(key, sessionId)
    }

    override fun recordValue(key: String, duration: Duration) {
        redisTemplate.opsForValue().set(key, duration.toMillis().toString())
    }

    override fun recordValue(key: String, value: Int) {
        redisTemplate.opsForValue().set(key, value.toString())
    }

    // 2. ПОИСКОВАЯ АНАЛИТИКА
    // Популярные запросы (топ-10)
    override fun incrementSortedSet(key: String, member: String, score: Double) {
        redisTemplate.opsForZSet().incrementScore(key, member, score)
    }

    // 4. СЕССИОННЫЙ АНАЛИЗ
    // Связываем поисковый запрос с сессией пользователя
    override fun addToSessionHistory(sessionId: String, value: String) {
        redisTemplate.opsForList().rightPush(sessionId, value)
    }
}