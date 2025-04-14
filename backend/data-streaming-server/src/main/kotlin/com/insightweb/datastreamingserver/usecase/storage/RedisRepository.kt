package com.insightweb.datastreamingserver.usecase.storage

import java.time.Duration

interface RedisRepository {
    fun increment(key: String)
    fun increment(key: String, amount: Double)
    fun addToSet(key: String, param: String)
    fun decrement(key: String)
    fun removeFromSet(key: String, sessionId: String)
    fun recordValue(key: String, duration: Duration)
    fun recordValue(key: String, value: Int)
    fun incrementSortedSet(key: String, member: String, score: Double)
    fun addToSessionHistory(sessionId: String, value: String)
}