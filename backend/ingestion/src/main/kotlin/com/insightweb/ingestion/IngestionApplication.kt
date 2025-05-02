package com.insightweb.ingestion

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IngestionApplication

fun main(args: Array<String>) {
    runApplication<IngestionApplication>(*args)
}
