package com.mycompany.shopping.product.service

import reactor.core.publisher.Mono
import com.mycompany.shopping.product.interfaces.BrandProductPriceStats

interface BrandStatsService {
    fun updateBrandStats(brandId: Long): Mono<Void>

    /**
     * Retrieves the brand with the lowest total price across all categories.
     *
     * @return A Mono emitting the brand stats with the lowest total price.
     */
    fun getMinTotalPriceBrandStats(): Mono<BrandProductPriceStats>
} 