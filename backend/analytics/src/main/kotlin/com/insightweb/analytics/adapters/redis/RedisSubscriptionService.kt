package com.insightweb.analytics.adapters.redis

import com.fasterxml.jackson.databind.ObjectMapper
import com.insightweb.analytics.domain.MetricType
import com.insightweb.analytics.usecase.repository.FastMetricsRepository
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap

@Service
class RedisSubscriptionService(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val container: RedisMessageListenerContainer,
    private val fastMetricsRepository: FastMetricsRepository,
    private val redisKeyConstructor: RedisKeyConstructor,
    private val objectMapper: ObjectMapper,
) {
    private val activeSubscriptions = ConcurrentHashMap<String, MutableSet<SseEmitter>>()
    private val keyListeners = ConcurrentHashMap<String, MessageListener>()

    fun subscribe(metricType: MetricType, url: String, emitter: SseEmitter) {
        val key = when (metricType) {
            MetricType.ACTIVE_SESSIONS -> redisKeyConstructor.activeSessions(url)
            MetricType.CLICKS -> redisKeyConstructor.recentClicks(url)
            MetricType.ERRORS -> redisKeyConstructor.errorStats(url, "")
            MetricType.SEARCHES -> redisKeyConstructor.searches(url)
            MetricType.CONVERSIONS -> redisKeyConstructor.conversions(url, "")
            MetricType.SCROLL -> redisKeyConstructor.scrollDepth(url)
            MetricType.GEO -> redisKeyConstructor.geoCountries(url)
            MetricType.DEVICES -> redisKeyConstructor.devices(url)
            MetricType.LANGUAGE -> redisKeyConstructor.languages(url)
        }
        activeSubscriptions.compute(key) { _, emitters ->
            (emitters ?: mutableSetOf()).apply {
                add(emitter)
                if (size == 1) setupListener(key)
            }
        }

        emitter.onCompletion { unsubscribe(key, emitter) }
        emitter.onTimeout { unsubscribe(key, emitter) }

        sendInitialData(metricType, url, emitter)
    }

    private fun setupListener(key: String, metricType: MetricType) {
        val listener = MessageListener { message, _ ->
            val changedKey = String(message.channel)
            val eventType = String(message.body)

            if (eventType == "set" || eventType == "del" || eventType == "hset") {
                val (metric, url) = parseMetricFromKey(key)
                val data = fetchUpdatedData(metric, url)
                notifySubscribers(key, KeyUpdateEvent(metric, url, data))
            }
        }

        val topic = ChannelTopic("__keyspace@0__:$metricType")
        container.addMessageListener(listener, topic)
    }

    private fun parseMetricFromKey(key: String): Pair<MetricType, String> {
        val parts = key.split(":")
        return MetricType.valueOf(parts[0]) to parts[1]
    }

    private fun fetchUpdatedData(metricType: MetricType, url: String): Any {
        return when (metricType) {
            MetricType.ACTIVE_SESSIONS -> fastMetricsRepository.getActiveSessions(url)
            MetricType.CLICKS -> fastMetricsRepository.getRecentClicksCount(url)
            MetricType.ERRORS -> fastMetricsRepository.getErrorStats(url, "CHROME")
            MetricType.SEARCHES -> fastMetricsRepository.getTopSearch(url)
            MetricType.CONVERSIONS -> fastMetricsRepository.getConversions(url, "")
            MetricType.SCROLL -> fastMetricsRepository.getAverageScrollDepth(url)
            MetricType.GEO -> fastMetricsRepository.getTopCountries(url)
            MetricType.DEVICES -> fastMetricsRepository.getDeviceDistribution(url)
            MetricType.LANGUAGE -> fastMetricsRepository.getLanguageDistribution(url)
        }
    }

    private fun sendInitialData(metricType: MetricType, url: String, emitter: SseEmitter) {
        try {
            val data = when (metricType) {
                MetricType.ACTIVE_SESSIONS -> fastMetricsRepository.getActiveSessions(url)
                MetricType.CLICKS -> fastMetricsRepository.getRecentClicksCount(url)
                MetricType.ERRORS -> fastMetricsRepository.getErrorStats(url, "CHROME")
                MetricType.SEARCHES -> fastMetricsRepository.getTopSearch(url)
                MetricType.CONVERSIONS -> fastMetricsRepository.getConversions(url, "")
                MetricType.SCROLL -> fastMetricsRepository.getAverageScrollDepth(url)
                MetricType.GEO -> fastMetricsRepository.getTopCountries(url)
                MetricType.DEVICES -> fastMetricsRepository.getDeviceDistribution(url)
                MetricType.LANGUAGE -> fastMetricsRepository.getLanguageDistribution(url)
            }
            emitter.send(KeyUpdateEvent(metricType, url, data))
        } catch (e: Exception) {
        }
    }

    private fun notifySubscribers(key: String, update: KeyUpdateEvent) {
        activeSubscriptions[key]?.forEach { emitter ->
            try {
                emitter.send(objectMapper.writeValueAsString(update))
            } catch (e: IOException) {
                unsubscribe(key, emitter)
            }
        }
    }

    private fun unsubscribe(key: String, emitter: SseEmitter) {
        activeSubscriptions.computeIfPresent(key) { _, emitters ->
            emitters.apply { remove(emitter) }.takeIf { it.isNotEmpty() } ?: run {
                container.removeMessageListener(getListener(key))
                null
            }
        }
    }
}