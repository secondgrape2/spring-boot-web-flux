package com.mycompany.shopping.brand

import com.mycompany.shopping.common.exception.CustomErrorCodes
import org.springframework.http.HttpStatus
import com.mycompany.shopping.common.exception.BaseHttpException

class BrandNotFoundException(
    message: String = "Brand not found"
) : BaseHttpException(
    message = message,
    statusCode = HttpStatus.UNPROCESSABLE_ENTITY,
    errorCode = CustomErrorCodes.BRAND_NOT_FOUND
) 