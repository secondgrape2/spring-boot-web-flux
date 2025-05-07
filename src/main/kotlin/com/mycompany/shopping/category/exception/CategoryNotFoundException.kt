package com.mycompany.shopping.category.exception

import com.mycompany.shopping.common.exception.BaseHttpException
import com.mycompany.shopping.common.exception.CustomErrorCodes
import org.springframework.http.HttpStatus

class CategoryNotFoundException(
    name: String
) : BaseHttpException(
    message = "Category not found with name: $name",
    statusCode = HttpStatus.BAD_REQUEST,
    errorCode = CustomErrorCodes.CATEGORY_NOT_FOUND
) 