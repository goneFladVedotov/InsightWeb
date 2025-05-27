package com.insightweb.analytics.usecase.cache

import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.ConcurrentHashMap

@Service
class FastMetricsCache {
    private val emitters = ConcurrentHashMap<String, SseEmitter>()

    fun get(key: String): SseEmitter = emitters.getValue(key)

    fun add(key: String, emitter: SseEmitter) {
        emitters[key] = emitter
    }

    fun remove(key: String) {
        emitters.remove(key)
    }
}