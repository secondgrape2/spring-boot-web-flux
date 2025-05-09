package com.mycompany.shopping.brand.repository

import com.mycompany.shopping.brand.interfaces.Brand
import com.mycompany.shopping.brand.interfaces.BrandRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Repository interface for managing brand data operations.
 * Provides methods for creating, updating, finding, and deleting brands.
 */
interface BrandRepository {
    /**
     * Creates a new brand in the repository.
     * @param brand The brand request containing brand details to create
     * @return A Mono emitting the created brand
     */
    fun create(brand: BrandRequest): Mono<Brand>
    
    /**
     * Updates an existing brand in the repository.
     * @param brand The brand entity with updated information
     * @return A Mono emitting the updated brand
     */
    fun update(brand: Brand): Mono<Brand>
    
    /**
     * Finds a brand by its ID.
     * @param id The ID of the brand to find
     * @return A Mono emitting the found brand, or empty if not found
     */
    fun findById(id: Long): Mono<Brand>

    /**
     * Finds a brand by its name.
     * @param name The name of the brand to find
     * @return A Mono emitting the found brand, or empty if not found
     */
    fun findByName(name: String): Mono<Brand>
    
    /**
     * Performs a soft delete of a brand by its ID.
     * @param id The ID of the brand to delete
     * @return A Mono completing when the deletion is done
     */
    fun softDelete(id: Long): Mono<Void>

    /**
     * Retrieves all brands.
     * @return A Flux emitting all brands
     */
    fun findAll(): Flux<Brand>
} 