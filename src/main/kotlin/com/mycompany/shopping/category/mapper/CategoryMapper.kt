package com.mycompany.shopping.category.mapper

import com.mycompany.shopping.category.dto.CategoryResponseDto
import com.mycompany.shopping.category.interfaces.Category
import com.mycompany.shopping.common.exception.InvalidFieldException

object CategoryMapper {
    fun toResponseDto(category: Category): CategoryResponseDto {
        val id = category.id ?: throw InvalidFieldException("Category ID is null")
        return CategoryResponseDto(
            id = id,
            name = category.name,
            localizedName = category.name.getLocalizedName("ko"),
            createdAt = category.createdAt,
            updatedAt = category.updatedAt
        )
    }
} 