package com.mycompany.shopping.category.exceptions

import com.mycompany.shopping.common.exception.BaseHttpException
import org.springframework.http.HttpStatus
import com.mycompany.shopping.common.exception.CustomErrorCodes

class CategoryNotFoundException(
    message: String = "Category not found"
) : BaseHttpException(
    message = message,
    statusCode = HttpStatus.UNPROCESSABLE_ENTITY,
    errorCode = CustomErrorCodes.CATEGORY_NOT_FOUND
) 