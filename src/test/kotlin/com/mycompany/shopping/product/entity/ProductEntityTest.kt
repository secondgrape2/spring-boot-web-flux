package com.mycompany.shopping.product.entity

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.time.Instant
import com.mycompany.shopping.common.exception.InvalidFieldException

class ProductEntityTest {

    @Test
    fun `toDomain should convert entity to domain model correctly`() {
        // given
        val now = Instant.now()
        val productEntity = ProductEntity(
            id = 1L,
            name = "Test Product",
            price = 10000L,
            brandId = 1L,
            categoryId = 1L,
            createdAt = now,
            updatedAt = now
        )

        // when
        val productDomain = productEntity.toDomain()

        // then
        assertEquals(1L, productDomain.id)
        assertEquals("Test Product", productDomain.name)
        assertEquals(10000L, productDomain.price)
        assertEquals(1L, productDomain.brandId)
        assertEquals(1L, productDomain.categoryId)
        assertEquals(now, productDomain.createdAt)
        assertEquals(now, productDomain.updatedAt)
    }

    @Test
    fun `toDomain should throw InvalidFieldException when id is null`() {
        // given
        val productEntity = ProductEntity(
            id = null,
            name = "Test Product",
            price = 10000L,
            brandId = 1L,
            categoryId = 1L
        )

        // when & then
        assertThrows(InvalidFieldException::class.java) {
            productEntity.toDomain()
        }
    }

    @Test
    fun `entity should be created with default values`() {
        // given
        val productEntity = ProductEntity(
            name = "Test Product",
            price = 10000L,
            brandId = 1L,
            categoryId = 1L
        )

        // then
        assertNull(productEntity.id)
        assertNotNull(productEntity.createdAt)
        assertNotNull(productEntity.updatedAt)
        assertNull(productEntity.deletedAt)
    }
} 