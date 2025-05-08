package com.mycompany.shopping.product.mapper

import com.mycompany.shopping.product.dto.ProductResponseDto
import com.mycompany.shopping.product.interfaces.Product
import org.springframework.stereotype.Component
import com.mycompany.shopping.common.exception.InvalidFieldException

@Component
class ProductMapper {
    fun toResponseDto(product: Product): ProductResponseDto {
        val id = product.id ?: throw InvalidFieldException("Product ID is null")
        return ProductResponseDto(
            id = id,
            name = product.name,
            price = product.price,
            brandId = product.brandId,
            categoryId = product.categoryId,
            createdAt = product.createdAt,
            updatedAt = product.updatedAt
        )
    }
} 