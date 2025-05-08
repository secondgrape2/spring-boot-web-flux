package com.mycompany.shopping.product.interfaces

import java.time.Instant

/**
 * Interface representing brand product price statistics in the shopping system.
 * Contains essential statistics information including brand identifier, total minimum price, and timestamps.
 */
interface BrandProductPriceStats {
    /** Unique identifier for the statistics */
    val id: Long?
    /** ID of the associated brand */
    val brandId: Long
    /** Total minimum price across all categories for the brand */
    val totalMinPrice: Long
    /** Timestamp when the statistics was created */
    val createdAt: Instant
    /** Timestamp when the statistics was last updated */
    val updatedAt: Instant
} 