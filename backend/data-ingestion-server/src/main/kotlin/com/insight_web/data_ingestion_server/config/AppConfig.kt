package com.insight_web.data_ingestion_server.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {
    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().registerModule(kotlinModule())
    }
}