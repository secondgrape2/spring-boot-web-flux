package com.mycompany.shopping.common.exception

/**
 * Response object for error handling.
 *
 * @property code Optional error code for client identification (e.g., E0001, E0002, E0003, E0004, E0005)
 * @property message Detailed error message describing what went wrong
 * @property statusCode HTTP status code associated with the error
 */
data class ErrorResponse(
    val code: String? = null,
    val message: String,
    val statusCode: Int
) 