package com.mycompany.shopping.product.service

import com.mycompany.shopping.product.dto.*
import reactor.core.publisher.Mono
import reactor.core.publisher.Flux
import com.mycompany.shopping.product.interfaces.Product

/**
 * Service interface for managing shopping products.
 * Defines operations related to product data retrieval and manipulation.
 */
interface ShoppingProductService {

    /**
     * Retrieves the minimum price for each product category.
     *
     * @return A Mono emitting [CategoryMinPriceResponseDto] objects, each representing the lowest priced product in a category.
     */
    fun getCategoryMinPricesWithTotalAmount(): Mono<CategoryMinPriceResponseDto>

    /**
     * Retrieves the brand that offers the lowest total price across all categories.
     *
     * @return A Mono emitting [BrandLowestPriceResponseDto] containing the brand with lowest total price and its products.
     */
    fun getBrandWithLowestTotalPrice(): Mono<BrandLowestPriceResponseDto>

    /**
     * Retrieves the lowest and highest priced brands and their products for a given category.
     *
     * @param category The category to search for
     * @return A Mono emitting [CategoryPriceRangeResponseDto] containing the lowest and highest priced products in the category
     */
    fun getCategoryPriceRange(categoryId: Long): Mono<CategoryPriceRangeResponseDto>
}