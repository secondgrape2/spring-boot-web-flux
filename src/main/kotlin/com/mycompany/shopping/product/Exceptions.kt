package com.mycompany.shopping.product.exceptions

import com.mycompany.shopping.common.exception.BaseHttpException
import org.springframework.http.HttpStatus
import com.mycompany.shopping.common.exception.CustomErrorCodes
import com.mycompany.shopping.product.domain.enums.Category

class InvalidCategoryException(
    message: String = "Invalid category name. Please use one of: ${Category.values().joinToString(", ") { it.name.lowercase() }}"
) : BaseHttpException(
    message = message,
    statusCode = HttpStatus.BAD_REQUEST,
    errorCode = CustomErrorCodes.INVALID_CATEGORY
)