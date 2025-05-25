package com.insightweb.ingestion.adapters.httpClient

import com.fasterxml.jackson.databind.ObjectMapper
import com.insightweb.ingestion.usecase.analyticsIntegration.AuthClient
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class HttpAuthClient(
    private val client: OkHttpClient,
    @Value("\${insightweb.auth.url}")
    private val authUrl: String
): AuthClient {
    override fun isSiteExisted(userId: String): Boolean {
        val request = Request.Builder()
            .url("$authUrl/$userId")
            .build()

        // Выполняем запрос
        return client.newCall(request).execute().use { response ->
            when (response.code) {
                204 -> {
                    true
                }
                404 -> {
                    false
                }
                else -> {
                    throw RuntimeException("Request is failed")
                }
            }
        }
    }
}