package com.insightweb.analytics.usecase.repository

import com.insightweb.analytics.domain.ConversionTrend
import java.time.LocalDate

interface AggregatedMetricsRepository {
    // 1. Средняя продолжительность сессий за день/неделю
    fun getAvgSessionDuration(url: String, periodStart: LocalDate, periodEnd: LocalDate): Map<LocalDate, Double>

    // 2. Топ-10 стран по сессиям
    fun getTopCountries(url: String): List<Pair<String, Int>>

    // 3. Распределение разрешений экранов
    fun getScreenResolutions(url: String): List<Pair<Int, Int>>

    // 4. Распределение браузеров и ОС
    fun getBrowserOsStats(url: String): List<Triple<String, String, Int>>

    // 5. Суммарная выручка по месяцам
    fun getMonthlyRevenue(url: String, year: Int): Map<LocalDate, Double>

    // 6. Тренды конверсий по типам
    fun getConversionTrends(url: String): List<ConversionTrend>

    // 7. Среднее количество результатов поиска
    fun getAvgSearchResults(url: String): Double

    // 8. Процент глубокой прокрутки
    fun getDeepScrollPercentage(url: String): Double

    // 9. Топ кликабельных элементов за месяц
    fun getTopClickableElements(url: String, month: LocalDate): List<Pair<String, Int>>

    // 10. Статистика ошибок по типам
    fun getErrorStats(url: String, month: LocalDate): List<Pair<String, Int>>
}