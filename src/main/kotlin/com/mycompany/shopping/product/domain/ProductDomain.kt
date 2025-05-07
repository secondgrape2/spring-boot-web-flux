package com.mycompany.shopping.product.domain

import com.mycompany.shopping.product.interfaces.Product
import java.time.Instant

data class ProductDomain(
    override val id: Long?,
    override val name: String,
    override val price: Long,
    override val brandId: Long,
    override val categoryId: Long,
    override val createdAt: Instant,
    override val updatedAt: Instant
) : Product 