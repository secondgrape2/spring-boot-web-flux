package com.mycompany.shopping.product.interfaces

import java.time.Instant

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