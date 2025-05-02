package com.insightweb.streaming.usecase.handler.event

import com.insightweb.domain.TrackingEvent
import kotlin.reflect.KClass

interface TrackingEventHandler<T: TrackingEvent> {
    val supportedEventType: KClass<out T>
    fun handle(event: T)
}