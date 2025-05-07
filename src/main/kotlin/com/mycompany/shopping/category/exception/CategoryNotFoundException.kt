package com.mycompany.shopping.category.exception

import com.mycompany.shopping.common.exception.BaseHttpException
import com.mycompany.shopping.common.exception.CustomErrorCodes
import org.springframework.http.HttpStatus

class CategoryNotFoundException(
    name: String
) : BaseHttpException(
    message = "Category not found with name: $name",
    statusCode = HttpStatus.NOT_FOUND,
    errorCode = CustomErrorCodes.CATEGORY_NOT_FOUND
) 