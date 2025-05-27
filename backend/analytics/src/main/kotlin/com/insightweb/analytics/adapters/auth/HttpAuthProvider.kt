package com.insightweb.analytics.adapters.auth

import com.fasterxml.jackson.databind.ObjectMapper
import com.insightweb.analytics.usecase.provider.AuthProvider
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import com.fasterxml.jackson.module.kotlin.readValue
import com.insightweb.analytics.domain.exception.ExpiredAuthTokenException

@Service
class HttpAuthProvider(
    private val httpClient: OkHttpClient,
    private val objectMapper: ObjectMapper,
    @Value("\${insightweb.auth.sites.url}")
    private val authUrl: String
): AuthProvider {
    override fun getAllUserSites(userToken: String): List<String> {
        val request = Request.Builder()
            .addHeader("Authorization", userToken)
            .url(authUrl)
            .get()
            .build()

        val response = httpClient.newCall(request).execute()

        return when (response.code) {
            200 -> objectMapper.readValue<List<SiteDto>>(response.body!!.string()).map { it.url }
            401 -> throw ExpiredAuthTokenException("Token $userToken is expired")
            else -> throw RuntimeException("internal-error")
        }
    }

    data class SiteDto(
        val name: String,
        val url: String,
    )
}