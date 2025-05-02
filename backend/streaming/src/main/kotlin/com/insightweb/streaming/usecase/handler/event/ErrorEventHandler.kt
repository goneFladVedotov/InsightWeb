package com.insightweb.streaming.usecase.handler.event

import com.insightweb.domain.ErrorEvent
import com.insightweb.streaming.usecase.TrackingEventScheduler
import com.insightweb.streaming.usecase.session.SessionHandleEventPublisher
import com.insightweb.streaming.usecase.storage.RedisRepository
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class ErrorEventHandler(
    private val trackingEventScheduler: TrackingEventScheduler,
    private val redisRepo: RedisRepository,
    private val sessionHandleEventPublisher: SessionHandleEventPublisher,

    ): TrackingEventHandler<ErrorEvent> {
    override val supportedEventType: KClass<out ErrorEvent> = ErrorEvent::class

    override fun handle(event: ErrorEvent) {
        // 1. ЛОГИРОВАНИЕ ОШИБОК
        // Сохраняем информацию об ошибке для последующего анализа
        trackingEventScheduler.addToDeque(event)

        // 2. МОНИТОРИНГ ОШИБОК
        // Увеличиваем счетчик ошибок данного типа
        redisRepo.increment("errors:${event.errorType}")

        sessionHandleEventPublisher.publish(event.sessionId)


        /*        // 3. КРИТИЧЕСКИЕ ОШИБКИ
                // Для критических ошибок инициируем алерт
                if (isCriticalError(event.errorType)) {
                    alertService.triggerErrorAlert(event)
                }*/
    }

    /**
     * Определяет, является ли ошибка критической
     * @param errorType тип ошибки
     * @return true если ошибка требует немедленного реагирования
     */
    private fun isCriticalError(errorType: String): Boolean {
        return errorType in setOf("payment_failed", "checkout_crash", "db_connection_lost")
    }

}