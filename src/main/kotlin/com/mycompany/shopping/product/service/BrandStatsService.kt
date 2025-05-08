package com.mycompany.shopping.product.service

import reactor.core.publisher.Mono

interface BrandStatsService {
    fun updateBrandStats(brandId: Long): Mono<Void>
} 