package com.mycompany.shopping.brand.interfaces
import java.time.Instant

/**
 * Interface representing a Brand entity in the shopping system.
 * Contains essential brand information including identifier, name, and timestamps.
 */
interface Brand {
    /** Unique identifier for the brand */
    val id: Long
    /** Name of the brand */
    val name: String
    /** Timestamp when the brand was created */
    val createdAt: Instant
    /** Timestamp when the brand was last updated */
    val updatedAt: Instant
}

/**
 * Interface representing a request to create or update a Brand.
 * Contains the minimum required information for brand operations.
 */
interface BrandRequest {
    /** Name of the brand to be created or updated */
    val name: String
}