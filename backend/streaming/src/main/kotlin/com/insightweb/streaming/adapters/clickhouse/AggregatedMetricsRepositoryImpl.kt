package com.insightweb.streaming.adapters.clickhouse

import com.insightweb.domain.ClickEvent
import com.insightweb.domain.ConversionEvent
import com.insightweb.domain.ErrorEvent
import com.insightweb.domain.PageViewEvent
import com.insightweb.domain.ScrollEvent
import com.insightweb.domain.SearchEvent
import com.insightweb.domain.SessionEndEvent
import com.insightweb.domain.TrackingEvent
import com.insightweb.streaming.usecase.repository.AggregatedMetricsRepository
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.Types
import java.time.ZoneId
import java.util.*

@Repository
class AggregatedMetricsRepositoryImpl(
    private val jdbcTemplate: JdbcTemplate,
): AggregatedMetricsRepository {
    override fun insertEvents(events: List<TrackingEvent>) {
        val sql = """
            INSERT INTO events (
                event_date, 
                event_time, 
                event_type, 
                session_id, 
                url, 
                referrer, 
                ip, 
                device_type, 
                browser, 
                os, 
                language,
                element_id,
                element_text,
                conversion_type,
                conversion_value,
                error_type,
                error_message,
                page_title,
                screen_width,
                scroll_depth,
                search_query,
                search_results_count,
                session_duration_ms
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """.trimIndent()

        try {
            jdbcTemplate.batchUpdate(sql, object : BatchPreparedStatementSetter {
                override fun setValues(ps: java.sql.PreparedStatement, i: Int) {
                    val event = events[i]
                    ps.setDate(1, java.sql.Date.valueOf(event.timestamp.atZone(ZoneId.systemDefault()).toLocalDate()))
                    ps.setObject(2, event.timestamp.atZone(ZoneId.systemDefault()).toLocalTime())
                    ps.setString(3, event.event)
                    ps.setString(4, event.sessionId)
                    ps.setString(5, event.url)
                    ps.setString(6, event.referrer)
                    ps.setString(7, event.ip)
                    ps.setString(8, event.deviceType.name)
                    ps.setString(9, event.browser)
                    ps.setString(10, event.os)
                    ps.setString(11, event.language)

                    (event as? ClickEvent)?.let {
                        ps.setString(12, it.elementId)
                        ps.setString(13, it.elementText)
                    } ?: run {
                        ps.setNull(12, Types.VARCHAR)
                        ps.setNull(13, Types.VARCHAR)
                    }

                    (event as? ConversionEvent)?.let {
                        ps.setString(14, it.type)
                        ps.setDouble(15, it.value ?: 0.0)
                    } ?: run {
                        ps.setNull(14, Types.VARCHAR)
                        ps.setNull(15, Types.DOUBLE)
                    }

                    (event as? ErrorEvent)?.let {
                        ps.setString(16, it.errorType)
                        ps.setString(17, it.message)
                    } ?: run {
                        ps.setNull(16, Types.VARCHAR)
                        ps.setNull(17, Types.VARCHAR)
                    }

                    (event as? PageViewEvent)?.let {
                        ps.setString(18, it.title)
                        ps.setInt(19, it.screenWidth ?: 0)
                    } ?: run {
                        ps.setNull(18, Types.VARCHAR)
                        ps.setNull(19, Types.INTEGER)
                    }

                    (event as? ScrollEvent)?.let {
                        ps.setInt(20, it.depth)
                    } ?: run {
                        ps.setNull(20, Types.INTEGER)
                    }

                    (event as? SearchEvent)?.let {
                        ps.setString(21, it.query)
                        ps.setInt(22, it.resultsCount ?: 0)
                    } ?: run {
                        ps.setNull(21, Types.VARCHAR)
                        ps.setNull(22, Types.INTEGER)
                    }

                    (event as? SessionEndEvent)?.let {
                        ps.setLong(23, it.durationMs)
                    } ?: run {
                        ps.setNull(23, Types.BIGINT)
                    } }

                override fun getBatchSize(): Int = events.size
            })
        } catch (e: Exception) {
            throw RuntimeException("ClickHouse batch insert failed", e)
        }
    }
}