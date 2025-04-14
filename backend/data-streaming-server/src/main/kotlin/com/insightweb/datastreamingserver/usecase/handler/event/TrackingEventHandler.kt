package com.insightweb.datastreamingserver.usecase.handler.event

import com.insight_web.domain.TrackingEvent
import kotlin.reflect.KClass

interface TrackingEventHandler<T: TrackingEvent> {
    val supportedEventType: KClass<out T>
    fun handle(event: T)
}