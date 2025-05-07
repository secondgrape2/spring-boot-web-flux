package com.mycompany.shopping.common.exception

import org.springframework.http.HttpStatus
import com.mycompany.shopping.common.exception.CustomErrorCodes

class InvalidFieldException(
    message: String = "Invalid field value."
) : BaseHttpException(
    message = message,
    statusCode = HttpStatus.UNPROCESSABLE_ENTITY,
    errorCode = CustomErrorCodes.INVALID_FIELD
)