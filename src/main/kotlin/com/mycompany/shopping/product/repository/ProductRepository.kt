package com.mycompany.shopping.product.repository

import com.mycompany.shopping.product.interfaces.Product
import reactor.core.publisher.Mono

/**
 * Repository interface for managing product data operations.
 * Provides methods for creating, updating, finding, and deleting products.
 */
interface ProductRepository {
    /**
     * Creates a new product in the repository.
     * @param product The product request containing product details to create
     * @return A Mono emitting the created product
     */
    fun create(product: Product): Mono<Product>
    
    /**
     * Updates an existing product in the repository.
     * @param product The product entity with updated information
     * @return A Mono emitting the updated product
     */
    fun update(product: Product): Mono<Product>
    
    /**
     * Finds a product by its ID.
     * @param id The ID of the product to find
     * @return A Mono emitting the found product, or empty if not found
     */
    fun findById(id: Long): Mono<Product>
    
    /**
     * Performs a soft delete of a product by its ID.
     * @param id The ID of the product to delete
     * @return A Mono completing when the deletion is done
     */
    fun softDelete(id: Long): Mono<Void>
} 