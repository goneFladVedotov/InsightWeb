package com.insightweb.streaming.adapters.clickhouse

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

@Configuration
class ClickHouseConfig {
    @Value("\${spring.clickhouse.url}")
    private lateinit var url: String

    @Value("\${spring.clickhouse.username}")
    private lateinit var username: String

    @Value("\${spring.clickhouse.password}")
    private lateinit var password: String

    @Bean
    fun clickHouseDataSource(): DataSource {
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:clickhouse://localhost:8123/default"
            driverClassName = "com.clickhouse.jdbc.ClickHouseDriver"
            username = "default"
            password = ""
        }
        return HikariDataSource(config)
    }

    @Bean
    fun clickHouseJdbcTemplate(dataSource: DataSource): JdbcTemplate {
        return JdbcTemplate(dataSource)
    }
}