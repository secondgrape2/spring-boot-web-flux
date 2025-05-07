package com.mycompany.shopping.common.exception

import com.mycompany.shopping.product.domain.enums.ProductCategory

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
    BRAND_NOT_FOUND(2000, "Brand not found"),

    // Category errors (2100 - 2199)
    CATEGORY_NOT_FOUND(2100, "Category not found"),
    INVALID_CATEGORY(2101, "Invalid category name. Please use one of: ${ProductCategory.values().joinToString(", ") { it.value.lowercase() }}");

    companion object {
        /**
         * Get error code string in format "E{code}"
         */
        fun getErrorCodeString(code: Int): String {
            return "E${code.toString().padStart(4, '0')}"
        }
    }
}