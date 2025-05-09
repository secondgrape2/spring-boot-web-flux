package com.mycompany.shopping.product.repository

import com.mycompany.shopping.product.interfaces.Product
import com.mycompany.shopping.product.interfaces.ProductWithBrand
import reactor.core.publisher.Mono
import reactor.core.publisher.Flux
import com.mycompany.shopping.product.domain.ProductDomain
import com.mycompany.shopping.product.domain.enums.ProductCategory
import com.mycompany.shopping.product.domain.MinMaxPriceProductWithBrandDomain

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
     * Finds all products.
     * @return A Flux emitting all products, excluding soft-deleted ones
     */
    fun findAll(): Flux<Product>
    
    /**
     * Finds the cheapest product by category ID.
     * @param categoryId The ID of the category to find the cheapest product for
     * @return A Mono emitting the cheapest product, or empty if not found
     */
    fun findCheapestProductsByCategory(): Flux<ProductWithBrand>
    
    /**
     * Performs a soft delete of a product by its ID.
     * @param id The ID of the product to delete
     * @return A Mono completing when the deletion is done
     */
    fun softDelete(id: Long): Mono<Void>

    /**
     * Finds the product with the minimum and maximum price for a given category ID.
     * @param categoryId The ID of the category to find the min/max price product for
     * @return A Mono emitting the product with the min/max price, or empty if not found
     */
    fun findMinMaxPriceProductsWithBrandByCategoryId(categoryId: Long): Mono<MinMaxPriceProductWithBrandDomain>

    /**
     * Calculates the sum of the minimum prices for all categories for a specific brand.
     * @param brandId The ID of the brand to calculate the sum for
     * @return A Mono emitting the sum of the minimum prices for all categories of the specified brand
     */
    fun calculateMinPriceSumByCategoryForBrand(brandId: Long): Mono<Long>

    /**
     * Finds the cheapest products by brand ID.
     * @param brandId The ID of the brand to find the cheapest products for
     * @return A Flux emitting the cheapest products, or empty if not found
     */
    fun findCheapestProductsByBrandId(brandId: Long): Flux<ProductWithBrand>
} 