package com.insightweb.streaming.usecase.repository

import com.insightweb.domain.DeviceType

interface FastMetricsRepository {
    // Активные сессии
    fun incrementActiveSessions(url: String)

    fun decrementActiveSessions(url: String)

    // Клики
    fun trackClick(url: String, elementId: String, ttlMinutes: Long = 15)

    // Ошибки
    fun trackError(url: String, errorType: String, browser: String, ttlMinutes: Long = 5)

    // Поисковые запросы
    fun trackSearch(url: String, query: String, ttlMinutes: Long = 30)

    // Конверсии
    fun trackConversion(url: String, type: String, value: Double?, ttlHours: Long = 1)

    // Глубина прокрутки
    fun updateScrollDepth(url: String, sessionId: String, depth: Int)

    // География
    fun updateGeo(url: String, country: String, city: String)

    // Устройства и языки
    fun trackDevice(url: String, deviceType: DeviceType)

    fun trackLanguage(url: String, language: String)
}