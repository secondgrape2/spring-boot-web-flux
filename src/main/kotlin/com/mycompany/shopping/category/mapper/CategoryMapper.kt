package com.mycompany.shopping.category.mapper

import com.mycompany.shopping.category.dto.CategoryResponseDto
import com.mycompany.shopping.category.interfaces.Category

object CategoryMapper {
    fun toResponseDto(category: Category): CategoryResponseDto {
        return CategoryResponseDto(
            id = category.id,
            name = category.name,
            createdAt = category.createdAt,
            updatedAt = category.updatedAt
        )
    }
} 