package com.insight_web.data_ingestion_server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DataIngestionServerApplication {
}

fun main(args: Array<String>) {
    runApplication<DataIngestionServerApplication>(*args)
}

