package com.insightweb.streaming.usecase

import com.insightweb.streaming.usecase.handler.event.TrackingEventHandler
import com.insightweb.streaming.usecase.handler.metrics.MetricsHandler
import com.insightweb.domain.TrackingEvent
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class TrackingEventProcessing(
    trackingEventHandlers: List<TrackingEventHandler<*>>,
    private val metricsHandlers: List<MetricsHandler>,
) {
    private val handlerByTrackingEvent: Map<KClass<out TrackingEvent>, TrackingEventHandler<TrackingEvent>> =
        trackingEventHandlers
            .filterIsInstance<TrackingEventHandler<TrackingEvent>>()
            .associateBy { it.supportedEventType }

    @Transactional
    fun process(event: TrackingEvent) {
        handlerByTrackingEvent.getValue(event::class).handle(event)
        metricsHandlers.forEach {
            it.handle(event)
        }
    }
}