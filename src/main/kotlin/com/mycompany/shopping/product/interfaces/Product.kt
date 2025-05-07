package com.mycompany.shopping.product.interfaces

import java.time.Instant
import com.mycompany.shopping.brand.interfaces.Brand
import com.mycompany.shopping.product.domain.enums.ProductCategory

/**
 * Interface representing a Product entity in the shopping system.
 * Contains essential product information including identifier, name, price, and relationships.
 */
interface Product {
    /** Unique identifier for the product */
    val id: Long?
    /** Name of the product */
    val name: String
    /** Price of the product */
    val price: Long
    /** ID of the associated brand */
    val brandId: Long
    /** ID of the associated category */
    val categoryId: Long
    /** Timestamp when the product was created */
    val createdAt: Instant
    /** Timestamp when the product was last updated */
    val updatedAt: Instant
}

/**
 * Interface representing a product with brand information.
 * Extends the base Product interface with additional brand-related data.
 */
interface ProductWithBrand : Product {
    /** Name of the associated brand */
    val brand: Brand

    /** Name of the associated category */
    val categoryName: ProductCategory
}