package com.mycompany.shopping.brand.entity

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.time.Instant
import com.mycompany.shopping.common.exception.InvalidFieldException

class BrandEntityTest {

    @Test
    fun `toDomain should convert BrandEntity to BrandDomain correctly`() {
        // given
        val now = Instant.now()
        val brandEntity = BrandEntity(
            id = 1L,
            name = "Test Brand",
            createdAt = now,
            updatedAt = now,
            deletedAt = null
        )

        // when
        val brandDomain = brandEntity.toDomain()

        // then
        assertEquals(1L, brandDomain.id)
        assertEquals("Test Brand", brandDomain.name)
        assertEquals(now, brandDomain.createdAt)
        assertEquals(now, brandDomain.updatedAt)
    }

    @Test
    fun `toDomain should throw InvalidFieldException when id is null`() {
        // given
        val brandEntity = BrandEntity(
            id = null,
            name = "Test Brand"
        )

        // when & then
        assertThrows(InvalidFieldException::class.java) {
            brandEntity.toDomain()
        }
    }
} 