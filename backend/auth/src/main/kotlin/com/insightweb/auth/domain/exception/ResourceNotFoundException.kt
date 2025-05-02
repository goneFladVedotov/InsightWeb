package com.insightweb.auth.domain.exception

class ResourceNotFoundException(
    override val message: String
): RuntimeException() {
}