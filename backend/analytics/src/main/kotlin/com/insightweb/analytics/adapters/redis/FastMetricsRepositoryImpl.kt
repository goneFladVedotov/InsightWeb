package com.insightweb.analytics.adapters.redis

import com.insightweb.analytics.usecase.repository.FastMetricsRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
class FastMetricsRepositoryImpl(
    private val redisTemplate: RedisTemplate<String, Any>
): FastMetricsRepository {
    override fun getActiveSessions(url: String): Long {
        return redisTemplate.opsForValue().get("site:$url:active_sessions")?.toString()?.toLong() ?: 0
    }

    override fun getRecentClicksCount(url: String): Long {
        val currentMinute = Instant.now().epochSecond / 60
        val keys = (0 until 15).map { "site:$url:clicks:${currentMinute - it}" }
        return redisTemplate.execute<Long> { connection ->
            connection.pfCount(*keys.map { it.toByteArray() }.toTypedArray())
        } ?: 0
    }

    override fun getErrorStats(url: String, browser: String): Long {
        return redisTemplate.opsForValue().get("site:$url:errors:$browser")?.toString()?.toLong() ?: 0
    }

    override fun getTopSearch(url: String, limit: Int): List<String> {
        return redisTemplate.opsForZSet()
            .reverseRange("site:$url:searches", 0, limit.toLong() - 1)
            ?.map { it.toString() } ?: emptyList()
    }

    override fun getConversions(url: String, type: String): Double {
        return redisTemplate.opsForValue().get("site:$url:conversions:$type")?.toString()?.toDouble() ?: 0.0
    }

    override fun getAverageScrollDepth(url: String): Double {
        val entries = redisTemplate.opsForHash<String, Int>().entries("site:$url:scroll_depth")
        return entries.values.map { it.toDouble() }.average()
    }

    override fun getTopCountries(url: String, limit: Int): Map<String, Double> {
        return redisTemplate.opsForZSet()
            .reverseRangeWithScores("site:$url:geo:countries", 0, limit.toLong() - 1)
            ?.associate { it.value.toString() to it.score!! } ?: emptyMap()
    }

    override fun getDeviceDistribution(url: String): Map<String, Int> {
        return redisTemplate.opsForHash<String, Int>().entries("site:$url:devices")
    }

    override fun getLanguageDistribution(url: String): Map<String, Int> {
        return redisTemplate.opsForHash<String, Int>().entries("site:$url:languages")
    }

    override fun checkActivitySpike(url: String, threshold: Long): Boolean {
        val current = getActiveSessions(url)
        val previous = redisTemplate.opsForValue().get("site:$url:prev_active_sessions")?.toString()?.toLong() ?: 0
        redisTemplate.opsForValue().set("site:$url:prev_active_sessions", current.toString())
        return current - previous > threshold
    }
}