package com.mycompany.shopping.product.mapper

import com.mycompany.shopping.product.dto.ProductResponseDto
import com.mycompany.shopping.product.interfaces.Product
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.time.Instant

class ProductMapperTest {

    private val productMapper = ProductMapper()

    @Test
    fun `toResponse should correctly map Product to ProductResponse`() {
        // given
        val now = Instant.now()
        val product = object : Product {
            override val id: Long = 1L
            override val name: String = "Test Product"
            override val price: Long = 10000
            override val brandId: Long = 1L
            override val categoryId: Long = 1L
            override val createdAt: Instant = now
            override val updatedAt: Instant = now
        }

        // when
        val result = productMapper.toResponseDto(product)

        // then
        assertNotNull(result)
        assertEquals(1L, result.id)
        assertEquals("Test Product", result.name)
        assertEquals(10000, result.price)
        assertEquals(1L, result.brandId)
        assertEquals(1L, result.categoryId)
        assertEquals(now, result.createdAt)
        assertEquals(now, result.updatedAt)
    }

    @Test
    fun `toResponse should handle different timestamps for createdAt and updatedAt`() {
        // given
        val createdAt = Instant.now()
        val updatedAt = createdAt.plusSeconds(3600) // 1 hour later
        val product = object : Product {
            override val id: Long = 1L
            override val name: String = "Test Product"
            override val price: Long = 10000
            override val brandId: Long = 1L
            override val categoryId: Long = 1L
            override val createdAt: Instant = createdAt
            override val updatedAt: Instant = updatedAt
        }

        // when
        val result = productMapper.toResponseDto(product)

        // then
        assertNotNull(result)
        assertEquals(createdAt, result.createdAt)
        assertEquals(updatedAt, result.updatedAt)
        assertNotEquals(result.createdAt, result.updatedAt)
    }
} 