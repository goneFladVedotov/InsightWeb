package com.insightweb.analytics.adapters.sse

import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap

@Service
class SseService {
    private val emitters = ConcurrentHashMap<String, SseEmitter>()

    fun addEmitter(clientId: String, emitter: SseEmitter) {
        emitters[clientId] = emitter

        emitter.onCompletion { emitters.remove(clientId) }
        emitter.onTimeout { emitters.remove(clientId) }
        emitter.onError { _ -> emitters.remove(clientId) }
    }

    fun sendUpdate(value: String?) {
        emitters.values.forEach { emitter ->
            try {
                emitter.send(
                    SseEmitter.event()
                        .data(value ?: "null")
                        .name("redis-update")
                )
            } catch (e: IOException) {
                emitter.completeWithError(e)
            }
        }
    }
}