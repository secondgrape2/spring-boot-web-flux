package com.mycompany.shopping.category.service

import com.mycompany.shopping.category.dto.CategoryResponseDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Service interface for managing category operations.
 */
interface CategoryService {
    /**
     * Retrieves all categories.
     * @return A Flux emitting all category responses
     */
    fun getAllCategories(): Flux<CategoryResponseDto>

    /**
     * Retrieves a category by its name.
     * @param name The name of the category to retrieve
     * @return A Mono emitting the category response
     */
    fun getCategoryByName(name: String): Mono<CategoryResponseDto>
} 