package com.mycompany.shopping.product.repository

import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import reactor.core.publisher.Mono
import com.mycompany.shopping.product.interfaces.BrandProductPriceStats

interface BrandProductPriceStatsRepository {
    fun findByBrandId(brandId: Long): Mono<BrandProductPriceStats>
    fun save(brandProductPriceStats: BrandProductPriceStats): Mono<BrandProductPriceStats>
    fun findByTotalMinPriceBrandStats(): Mono<BrandProductPriceStats>
    fun delete(id: Long): Mono<Void>
} 