package com.insightweb.streaming

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class StreamingApplication

fun main(args: Array<String>) {
    runApplication<StreamingApplication>(*args)
}
