package com.mycompany.shopping.product.domain

import com.mycompany.shopping.product.interfaces.BrandProductPriceStats
import java.time.Instant

/**
 * Domain class representing brand product price statistics.
 * Implements the BrandProductPriceStats interface and provides concrete implementation.
 */
data class BrandProductPriceStatsDomain(
    override val id: Long?,
    override val brandId: Long,
    override val totalMinPrice: Long,
    override val createdAt: Instant,
    override val updatedAt: Instant
) : BrandProductPriceStats 