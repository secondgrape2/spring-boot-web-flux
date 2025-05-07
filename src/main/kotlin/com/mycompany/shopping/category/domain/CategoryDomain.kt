package com.mycompany.shopping.category.domain

import com.mycompany.shopping.category.interfaces.Category
import com.mycompany.shopping.product.domain.enums.ProductCategory
import java.time.Instant

data class CategoryDomain(
    override val id: Long?,
    override val name: ProductCategory,
    override val createdAt: Instant,
    override val updatedAt: Instant
) : Category 