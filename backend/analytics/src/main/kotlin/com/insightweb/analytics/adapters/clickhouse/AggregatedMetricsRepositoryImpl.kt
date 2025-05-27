package com.insightweb.analytics.adapters.clickhouse

import com.insightweb.analytics.usecase.repository.AggregatedMetricsRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class AggregatedMetricsRepositoryImpl(
    private val jdbcTemplate: JdbcTemplate,
): AggregatedMetricsRepository {
    override fun getAvgSessionDuration(url: String, periodStart: LocalDate, periodEnd: LocalDate): Map<LocalDate, Double> {
        val sql = """
            SELECT 
                toDate(event_time) AS date,
                avg(session_duration_ms) / 1000 AS avg_duration_seconds
            FROM events
            WHERE
                event_type = 'SESSION_END' AND
                url = ? AND
                event_date BETWEEN ? AND ?
            GROUP BY date
        """
        return jdbcTemplate.query(sql, { rs, _ ->
            rs.getDate("date").toLocalDate() to rs.getDouble("avg_duration_seconds")
        }, url, periodStart, periodEnd).toMap()
    }

    override fun getTopCountries(url: String): List<Pair<String, Int>> {
        val sql = """
            SELECT
                ipToCountry(ip) AS country,
                count() AS sessions
            FROM events
            WHERE
                event_type = 'SESSION_START' AND
                url = ?
            GROUP BY country
            ORDER BY sessions DESC
            LIMIT 10
        """
        return jdbcTemplate.query(sql, { rs, _ ->
            rs.getString("country") to rs.getInt("sessions")
        }, url)
    }

    override fun getScreenResolutions(url: String): List<Pair<Int, Int>> {
        val sql = """
            SELECT
                screen_width,
                count() AS page_views
            FROM events
            WHERE
                event_type = 'PAGE_VIEW' AND
                screen_width IS NOT NULL AND
                url = ?
            GROUP BY screen_width
            ORDER BY page_views DESC
            LIMIT 5
        """
        return jdbcTemplate.query(sql, { rs, _ ->
            rs.getInt("screen_width") to rs.getInt("page_views")
        }, url)
    }

    override fun getBrowserOsStats(url: String): List<Triple<String, String, Int>> {
        val sql = """
            SELECT
                browser,
                os,
                count() AS sessions
            FROM events
            WHERE
                event_type = 'SESSION_START' AND
                url = ?
            GROUP BY browser, os
            ORDER BY sessions DESC
        """
        return jdbcTemplate.query(sql, { rs, _ ->
            Triple(
                rs.getString("browser"),
                rs.getString("os"),
                rs.getInt("sessions")
            )
        }, url)
    }

    override fun getMonthlyRevenue(url: String, year: Int): Map<LocalDate, Double> {
        val sql = """
            SELECT
                toStartOfMonth(event_time) AS month,
                sum(conversion_value) AS total_revenue
            FROM events
            WHERE
                event_type = 'CONVERSION' AND
                conversion_value IS NOT NULL AND
                url = ? AND
                toYear(event_time) = ?
            GROUP BY month
        """
        return jdbcTemplate.query(sql, { rs, _ ->
            rs.getDate("month").toLocalDate() to rs.getDouble("total_revenue")
        }, url, year).toMap()
    }

    override fun getConversionTrends(url: String): List<ConversionTrend> {
        val sql = """
            SELECT
                toDate(event_time) AS date,
                conversion_type,
                count() AS conversions
            FROM events
            WHERE
                event_type = 'CONVERSION' AND
                url = ?
            GROUP BY date, conversion_type
            ORDER BY date
        """
        return jdbcTemplate.query(sql, { rs, _ ->
            ConversionTrend(
                date = rs.getDate("date").toLocalDate(),
                type = rs.getString("conversion_type"),
                count = rs.getInt("conversions")
            )
        }, url)
    }

    override fun getAvgSearchResults(url: String): Double {
        val sql = """
            SELECT
                avg(search_results_count) AS avg_results
            FROM events
            WHERE
                event_type = 'SEARCH' AND
                search_results_count IS NOT NULL AND
                url = ?
        """
        return jdbcTemplate.queryForObject(sql, Double::class.java, url) ?: 0.0
    }

    override fun getDeepScrollPercentage(url: String): Double {
        val sql = """
            SELECT
                (countIf(scroll_depth >= 75) / count()) * 100 AS pct_deep_scroll
            FROM events
            WHERE
                event_type = 'SCROLL' AND
                url = ?
        """
        return jdbcTemplate.queryForObject(sql, Double::class.java, url) ?: 0.0
    }

    override fun getTopClickableElements(url: String, month: LocalDate): List<Pair<String, Int>> {
        val sql = """
            SELECT
                element_id,
                count() AS clicks
            FROM events
            WHERE
                event_type = 'CLICK' AND
                element_id IS NOT NULL AND
                url = ? AND
                toStartOfMonth(event_time) = ?
            GROUP BY element_id
            ORDER BY clicks DESC
            LIMIT 10
        """
        return jdbcTemplate.query(sql, { rs, _ ->
            rs.getString("element_id") to rs.getInt("clicks")
        }, url, month)
    }

    // 10. Статистика ошибок по типам
    override fun getErrorStats(url: String, month: LocalDate): List<Pair<String, Int>> {
        val sql = """
            SELECT
                error_type,
                count() AS error_count
            FROM events
            WHERE
                event_type = 'ERROR' AND
                url = ? AND
                toStartOfMonth(event_time) = ?
            GROUP BY error_type
            ORDER BY error_count DESC
        """
        return jdbcTemplate.query(sql, { rs, _ ->
            rs.getString("error_type") to rs.getInt("error_count")
        }, url, month)
    }
}