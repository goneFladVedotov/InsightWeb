package com.insight_web.blackbox.adapters.rest.controller;

import com.insight_web.blackbox.adapters.rest.dto.ExceptionDto;
import com.insight_web.blackbox.domain.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BlackboxExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleResourceNotFound(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDto(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleOthers(Exception e) {
        return ResponseEntity.internalServerError().body(new ExceptionDto(e.getMessage()));
    }
}
