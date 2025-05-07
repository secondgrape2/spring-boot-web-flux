package com.mycompany.shopping.category.interfaces

import java.time.Instant
import com.mycompany.shopping.product.domain.enums.ProductCategory

/**
 * Interface representing a Category entity in the shopping system.
 * Contains essential category information including identifier, name, and timestamps.
 */
interface Category {
    /** Unique identifier for the category */
    val id: Long?
    /** Name of the category */
    val name: ProductCategory
    /** Timestamp when the category was created */
    val createdAt: Instant
    /** Timestamp when the category was last updated */
    val updatedAt: Instant
} 