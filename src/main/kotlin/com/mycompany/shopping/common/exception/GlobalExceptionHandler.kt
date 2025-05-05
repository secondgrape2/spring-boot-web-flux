package com.mycompany.shopping.common.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BaseHttpException::class)
    fun handleBaseHttpException(ex: BaseHttpException): Mono<ResponseEntity<ErrorResponse>> {
        val errorResponse = ErrorResponse(
            code = ex.errorCode?.let { CustomErrorCodes.getErrorCodeString(it.code) },
            message = ex.message,
            statusCode = ex.statusCode.value()
        )
        return Mono.just(ResponseEntity.status(ex.statusCode).body(errorResponse))
    }

    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(ex: ResponseStatusException): Mono<ResponseEntity<ErrorResponse>> {
        val wrappedException = ResponseStatusExceptionWrapper(ex)
        return handleBaseHttpException(wrappedException)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): Mono<ResponseEntity<ErrorResponse>> {
        val errorResponse = ErrorResponse(
            code = null,
            message = ex.message ?: "An unexpected error occurred",
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value()
        )
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse))
    }
} 