package com.mycompany.shopping.product.exceptions

import com.mycompany.shopping.common.exception.BaseHttpException
import org.springframework.http.HttpStatus
import com.mycompany.shopping.common.exception.CustomErrorCodes

class ProductNotFoundException(
    message: String = "Product not found"
) : BaseHttpException(
    message = message,
    statusCode = HttpStatus.UNPROCESSABLE_ENTITY,
    errorCode = CustomErrorCodes.PRODUCT_NOT_FOUND
) 