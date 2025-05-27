package com.insightweb.analytics.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AnalyticsConfig {
    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().registerModule(kotlinModule())
    }

    @Bean
    fun okHttpClient(): OkHttpClient = OkHttpClient()
}