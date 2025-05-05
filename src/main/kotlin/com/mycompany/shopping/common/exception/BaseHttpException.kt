package com.mycompany.shopping.common.exception

import org.springframework.http.HttpStatus

/**
 * Base exception class for all application exceptions.
 * This class serves as a parent for all custom exceptions in the application.
 *
 * @property message Detailed error message
 * @property statusCode The HTTP status code associated with this exception
 * @property errorCode Optional custom error code for more detailed error classification
 */
abstract class BaseHttpException(
    override val message: String,
    val statusCode: HttpStatus,
    val errorCode: CustomErrorCodes? = null
) : RuntimeException(message)
