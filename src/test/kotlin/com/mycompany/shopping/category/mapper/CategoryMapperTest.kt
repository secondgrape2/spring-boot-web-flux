package com.mycompany.shopping.category.mapper

import com.mycompany.shopping.category.domain.CategoryDomain
import com.mycompany.shopping.category.dto.CategoryResponseDto
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.time.Instant
import com.mycompany.shopping.product.domain.enums.ProductCategory

class CategoryMapperTest {

    @Test
    fun `toResponseDto should correctly map Category to CategoryResponseDto with same timestamps`() {
        // given
        val now = Instant.now()
        val category = CategoryDomain(
            id = 1L,
            name = ProductCategory.TOP,
            createdAt = now,
            updatedAt = now
        )

        // when
        val result = CategoryMapper.toResponseDto(category)

        // then
        val expected = CategoryResponseDto(
            id = 1L,
            name = ProductCategory.TOP,
            createdAt = now,
            updatedAt = now
        )
        assertEquals(expected, result)
    }

    @Test
    fun `toResponseDto should correctly map Category to CategoryResponseDto with different timestamps`() {
        // given
        val createdAt = Instant.parse("2024-01-01T00:00:00Z")
        val updatedAt = Instant.parse("2024-01-02T00:00:00Z")
        val category = CategoryDomain(
            id = 1L,
            name = ProductCategory.TOP,
            createdAt = createdAt,
            updatedAt = updatedAt
        )

        // when
        val result = CategoryMapper.toResponseDto(category)

        // then
        val expected = CategoryResponseDto(
            id = 1L,
            name = ProductCategory.TOP,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
        assertEquals(expected, result)
    }

    @Test
    fun `toResponseDto should handle different category names`() {
        // given
        val now = Instant.now()
        val category = CategoryDomain(
            id = 1L,
            name = ProductCategory.TOP,
            createdAt = now,
            updatedAt = now
        )

        // when
        val result = CategoryMapper.toResponseDto(category)

        // then
        val expected = CategoryResponseDto(
            id = 1L,
            name = ProductCategory.TOP,
            createdAt = now,
            updatedAt = now
        )
        assertEquals(expected, result)
    }
} 