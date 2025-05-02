package com.insightweb.streaming.adapters.clickhouse

import com.insightweb.domain.TrackingEvent
import com.insightweb.streaming.usecase.storage.ClickHouseRepository
import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ClickHouseRepositoryImpl(
    private val jdbcTemplate: JdbcTemplate,
): ClickHouseRepository {
    override fun insertBatch(events: List<TrackingEvent>) {
        val sql = """
            INSERT INTO events (
                event_id, event_type, session_id, 
                url, timestamp, referrer
            ) VALUES (?, ?, ?, ?, ?, ?)
        """.trimIndent()

        try {
            jdbcTemplate.batchUpdate(sql, object : BatchPreparedStatementSetter {
                override fun setValues(ps: java.sql.PreparedStatement, i: Int) {
                    val event = events[i]
                    with(event) {
                        ps.setObject(1, UUID.randomUUID().timestamp())  // Генерация уникального event_id
                        ps.setString(2, event::class.simpleName)  // Тип события
                        ps.setString(3, sessionId)  // session_id
                        ps.setString(4, url)  // URL
                        ps.setTimestamp(5, java.sql.Timestamp.from(timestamp))  // timestamp
                        ps.setString(6, referrer)  // referrer
                    }
                }

                override fun getBatchSize(): Int = events.size
            })
        } catch (e: Exception) {
            throw RuntimeException("ClickHouse batch insert failed", e)
        }
    }
}