package com.insightweb.analytics.usecase.repository

interface FastMetricsRepository {
    // Активные сессии
    fun getActiveSessions(url: String): Long

    // Клики (последние 15 минут)
    fun getRecentClicksCount(url: String): Long

    // Ошибки по браузеру
    fun getErrorStats(url: String, browser: String): Long

    // Топ поисковых запросов
    fun getTopSearch(url: String, limit: Int = 5): String

    // Конверсии по типу
    fun getConversions(url: String, type: String): Double
    
    // Средняя глубина прокрутки
    fun getAverageScrollDepth(url: String): Double

    // Гео-данные
    fun getTopCountries(url: String, limit: Int = 5): Map<String, Double>

    // Распределение устройств
    fun getDeviceDistribution(url: String): Map<String, Int>

    // Языковые предпочтения
    fun getLanguageDistribution(url: String): Map<String, Int> 

    // Проверка всплеска активности
    fun checkActivitySpike(url: String, threshold: Long): Boolean 
}