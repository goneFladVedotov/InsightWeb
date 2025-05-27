package com.insightweb.analytics.usecase.provider

interface AuthProvider {
    fun getAllUserSites(userToken: String): List<String>
}