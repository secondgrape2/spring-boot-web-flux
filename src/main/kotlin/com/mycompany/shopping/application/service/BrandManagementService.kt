package com.mycompany.shopping.application.service

import com.mycompany.shopping.brand.service.BrandService
import com.mycompany.shopping.product.service.ProductService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import org.springframework.transaction.annotation.Transactional

/**
 * Application service for managing brand-related operations that involve multiple domains.
 * This service coordinates operations between brand and product domains.
 */
@Service
class BrandManagementService(
    private val brandService: BrandService,
    private val productService: ProductService
) {
    /**
     * Deletes a brand and all its associated products.
     * This operation performs a soft delete on both the brand and its products.
     * Both operations are executed in a single transaction.
     *
     * @param brandId The ID of the brand to delete
     * @return A Mono completing when both the brand and its products are deleted
     */
    @Transactional
    fun deleteBrandWithProducts(brandId: Long): Mono<Void> {
        return brandService.deleteBrand(brandId)
            .then(productService.softDeleteByBrandId(brandId).then())
    }
} 