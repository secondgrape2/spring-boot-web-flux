package com.mycompany.shopping.product.service

import com.mycompany.shopping.product.dto.*
import com.mycompany.shopping.product.domain.enums.Category
import reactor.core.publisher.Mono

/**
 * Service interface for managing products.
 * Defines operations related to product data retrieval and manipulation.
 */
interface ProductService {

    /**
     * Retrieves the minimum price for each product category.
     *
     * @return A Mono emitting [CategoryMinPriceResponse] objects, each representing the lowest priced product in a category.
     */
    fun getCategoryMinPricesWithTotalAmount(): Mono<CategoryMinPriceResponse>

    /**
     * Retrieves the brand that offers the lowest total price across all categories.
     *
     * @return A Mono emitting [BrandLowestPriceResponse] containing the brand with lowest total price and its products.
     */
    fun getBrandWithLowestTotalPrice(): Mono<BrandLowestPriceResponse>

    /**
     * Retrieves the lowest and highest priced brands and their products for a given category.
     *
     * @param category The category to search for
     * @return A Mono emitting [CategoryPriceRangeResponse] containing the lowest and highest priced products in the category
     */
    fun getCategoryPriceRange(category: Category): Mono<CategoryPriceRangeResponse>
}