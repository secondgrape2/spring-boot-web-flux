package com.mycompany.shopping.category.entity

import com.mycompany.shopping.product.domain.enums.ProductCategory
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CategoryEntityTest {

    @Test
    fun `toDomain should convert CategoryEntity to CategoryDomain successfully`() {
        // given
        val now = Instant.now()
        val categoryEntity = CategoryEntity(
            id = 1L,
            name = ProductCategory.TOP,
            createdAt = now,
            updatedAt = now
        )

        // when
        val categoryDomain = categoryEntity.toDomain()

        // then
        assertNotNull(categoryDomain)
        assertEquals(1L, categoryDomain.id)
        assertEquals(ProductCategory.TOP, categoryDomain.name)
        assertEquals(now, categoryDomain.createdAt)
        assertEquals(now, categoryDomain.updatedAt)
    }

    @Test
    fun `toDomain should throw IllegalStateException when id is null`() {
        // given
        val categoryEntity = CategoryEntity(
            id = null,
            name = ProductCategory.TOP
        )

        // when & then
        assertThrows<IllegalStateException> {
            categoryEntity.toDomain()
        }
    }
} 