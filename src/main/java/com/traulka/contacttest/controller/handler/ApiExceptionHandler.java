package com.traulka.contacttest.controller.handler;

import com.traulka.contacttest.exception.ApiExceptionResponse;
import com.traulka.contacttest.exception.ApiValidationError;
import com.traulka.contacttest.exception.DataNotFoundException;
import com.traulka.contacttest.exception.ValidationExceptionBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {DataNotFoundException.class})
    public ResponseEntity<ApiExceptionResponse> handleDataNotFoundException(DataNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(createApiError(exception.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiValidationError> handleException(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest().body(createValidationError(exception));
    }

    private ApiExceptionResponse createApiError(String exceptionMessage, HttpStatus status) {
        return new ApiExceptionResponse(exceptionMessage,
                status,
                LocalDateTime.now());
    }

    private ApiValidationError createValidationError(MethodArgumentNotValidException e) {
        return ValidationExceptionBuilder.fromBindingErrors(e.getBindingResult());
    }
}
