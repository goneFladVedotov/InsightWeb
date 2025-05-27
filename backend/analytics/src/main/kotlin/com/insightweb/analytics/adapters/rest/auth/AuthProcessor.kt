package com.insightweb.analytics.adapters.rest.auth

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.insightweb.analytics.domain.exception.ExpiredAuthTokenException
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class AuthProcessor(
    private val httpClient: OkHttpClient,
    private val objectMapper: ObjectMapper,
    @Value("\${insightweb.auth.puid.url}")
    private val authUrl: String
) {
    fun getPuid(token: String): Long {
        val request = Request.Builder()
            .addHeader("Authorization", token)
            .url(authUrl)
            .get()
            .build()

        val response = httpClient.newCall(request).execute()

        return when (response.code) {
            200 -> objectMapper.readValue<PuidDto>(response.body!!.string()).puid
            401 -> throw ExpiredAuthTokenException("Token $token is expired")
            else -> throw RuntimeException("internal-error")
        }
    }

    private data class PuidDto(val puid: Long)
}