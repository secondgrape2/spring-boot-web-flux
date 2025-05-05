package com.mycompany.shopping.common.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class ResponseStatusExceptionWrapper(
    private val originalException: ResponseStatusException
) : BaseHttpException(
    message = originalException.reason ?: "Unknown error",
    statusCode = HttpStatus.valueOf(originalException.statusCode.value()),
    errorCode = null
) 