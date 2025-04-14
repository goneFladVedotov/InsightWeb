package com.insightweb.datastreamingserver.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class AppConfig {
    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().registerModule(kotlinModule())
    }

    @Bean
    fun restTemplate() = RestTemplate()
}