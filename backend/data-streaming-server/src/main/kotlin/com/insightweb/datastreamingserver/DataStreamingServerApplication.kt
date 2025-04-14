package com.insightweb.datastreamingserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class DataStreamingServerApplication {
}

fun main(args: Array<String>) {
    runApplication<DataStreamingServerApplication>(*args)
}