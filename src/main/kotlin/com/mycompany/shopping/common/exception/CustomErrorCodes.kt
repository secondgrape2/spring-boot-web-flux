package com.mycompany.shopping.common.exception

/**
 * Custom error codes for the application.
 * Each domain has its own range of error codes.
 * 
 * These custom error codes exist for handling 400-level errors.
 *
 * @property code Numeric error code
 * @property description Detailed description of the error
 */
enum class CustomErrorCodes(
    val code: Int,
    val description: String
) {
    // Brand errors (2000 - 2099)
    BRAND_NOT_FOUND(2000, "Brand not found");

    companion object {
        /**
         * Get error code string in format "E{code}"
         */
        fun getErrorCodeString(code: Int): String {
            return "E${code.toString().padStart(4, '0')}"
        }
    }
} 