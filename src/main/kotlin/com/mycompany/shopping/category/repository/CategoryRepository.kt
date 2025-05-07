package com.mycompany.shopping.category.repository

import com.mycompany.shopping.category.interfaces.Category
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Repository interface for managing category data operations.
 * Provides methods for finding categories.
 */
interface CategoryRepository {
    /**
     * Finds all categories.
     * @return A Flux emitting all categories
     */
    fun findAll(): Flux<Category>

    /**
     * Finds a category by its name.
     * @param name The name of the category to find
     * @return A Mono emitting the found category, or empty if not found
     */
    fun findByName(name: String): Mono<Category>

    /**
     * Finds a category by its ID.
     * @param id The ID of the category to find
     * @return A Mono emitting the found category, or empty if not found
     */
    fun findById(id: Long): Mono<Category>
} 