package com.insightweb.streaming.usecase

import com.insightweb.streaming.usecase.handler.event.TrackingEventHandler
import com.insightweb.domain.TrackingEvent
import com.insightweb.streaming.usecase.repository.FastMetricsRepository
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class TrackingEventProcessing(
    trackingEventHandlers: List<TrackingEventHandler<*>>,
    private val trackingEventScheduler: TrackingEventScheduler,
    private val fastMetricsRepository: FastMetricsRepository,
) {
    private val handlerByTrackingEvent: Map<KClass<out TrackingEvent>, TrackingEventHandler<TrackingEvent>> =
        trackingEventHandlers
            .filterIsInstance<TrackingEventHandler<TrackingEvent>>()
            .associateBy { it.supportedEventType }

    fun process(event: TrackingEvent) {
        handlerByTrackingEvent.getValue(event::class).handle(event)
        trackingEventScheduler.addToDeque(event)

        fastMetricsRepository.trackDevice(event.url, event.deviceType)
        fastMetricsRepository.trackLanguage(event.url, event.language)
    }
}