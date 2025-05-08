package com.mycompany.shopping.product.service

import com.mycompany.shopping.product.dto.*
import reactor.core.publisher.Mono
import reactor.core.publisher.Flux
import com.mycompany.shopping.product.interfaces.Product
import com.mycompany.shopping.product.interfaces.ProductWithBrand
import com.mycompany.shopping.product.domain.MinMaxPriceProductWithBrandDomain

/**
 * Service interface for managing products.
 * Defines operations related to product data retrieval and manipulation.
 */
interface ProductService {
    /**
     * Creates a new product.
     *
     * @param request The request containing product details
     * @return A Mono emitting the created product response
     */
    fun createProduct(request: CreateProductRequestDto): Mono<ProductResponseDto>

    /**
     * Updates an existing product.
     *
     * @param id The ID of the product to update
     * @param request The request containing updated product details
     * @return A Mono emitting the updated product response
     */
    fun updateProduct(id: Long, request: UpdateProductRequestDto): Mono<ProductResponseDto>

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete
     * @return A Mono completing when the deletion is done
     */
    fun deleteProduct(id: Long): Mono<Void>

    /**
     * Retrieves the cheapest products by category.
     *
     * @return A Flux emitting [ProductWithBrand] objects, each representing the cheapest product in a category.
     */
    fun findCheapestProductsByCategory(): Flux<ProductWithBrand>

    /**
     * Retrieves the minimum and maximum price products with brand by category ID.
     *
     * @param categoryId The ID of the category to search for
     * @return A Mono emitting [MinMaxPriceProductWithBrandDomain] objects, each representing the minimum and maximum price products with brand in a category.
     */
    fun findMinMaxPriceProductsWithBrandByCategoryId(categoryId: Long): Mono<MinMaxPriceProductWithBrandDomain>

    /**
     * Calculates the minimum price sum by category for a brand.
     *
     * @param brandId The ID of the brand to calculate the sum for
     * @return A Mono emitting the calculated minimum price sum
     */
    fun calculateMinPriceSumByCategoryForBrand(brandId: Long): Mono<Long>

    /**
     * Retrieves the cheapest products by brand ID.
     *
     * @param brandId The ID of the brand to find the cheapest products for
     * @return A Flux emitting [ProductWithBrand] objects, each representing the cheapest product in a brand.
     */
    fun findCheapestProductsByBrandId(brandId: Long): Flux<ProductWithBrand>
}