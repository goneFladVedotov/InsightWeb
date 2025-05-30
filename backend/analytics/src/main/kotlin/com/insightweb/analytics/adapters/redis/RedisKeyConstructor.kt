package com.insightweb.analytics.adapters.redis

import org.springframework.stereotype.Service
import java.time.Instant

@Service
class RedisKeyConstructor {
    private fun basePrefix(url: String) = "site:$url"

    fun activeSessions(url: String) = "${basePrefix(url)}:active_sessions"

    fun recentClicks(url: String, minuteOffset: Long = 0): String {
        val currentMinute = Instant.now().epochSecond / 60 + minuteOffset
        return "${basePrefix(url)}:clicks:$currentMinute"
    }

    fun errorStats(url: String, browser: String) = "${basePrefix(url)}:errors:$browser"

    fun searches(url: String) = "${basePrefix(url)}:searches"

    fun conversions(url: String, type: String) = "${basePrefix(url)}:conversions:$type"

    fun scrollDepth(url: String) = "${basePrefix(url)}:scroll_depth"

    fun geoCountries(url: String) = "${basePrefix(url)}:geo:countries"

    fun devices(url: String) = "${basePrefix(url)}:devices"

    fun languages(url: String) = "${basePrefix(url)}:languages"

    fun previousActiveSessions(url: String) = "${basePrefix(url)}:prev_active_sessions"
}
