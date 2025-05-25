package com.insightweb.auth.adapters.rest.controller

import com.insightweb.auth.adapters.rest.dto.ExceptionDto
import com.insightweb.auth.domain.exception.ResourceNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class AuthExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFound(e: ResourceNotFoundException): ResponseEntity<ExceptionDto> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionDto(e.message))
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthentication(e: AuthenticationException): ResponseEntity<ExceptionDto> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ExceptionDto(e.message!!))
    }

    @ExceptionHandler(Exception::class)
    fun handleOthers(e: Exception): ResponseEntity<ExceptionDto> {
        return ResponseEntity.internalServerError().body(ExceptionDto(e.message!!))
    }
}