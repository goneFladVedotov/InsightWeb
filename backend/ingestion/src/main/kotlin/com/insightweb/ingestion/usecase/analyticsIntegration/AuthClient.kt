package com.insightweb.ingestion.usecase.analyticsIntegration

interface AuthClient {
    fun isSiteExisted(userId: String): Boolean
}