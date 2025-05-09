package com.mycompany.shopping.product.service.impl

import com.mycompany.shopping.product.repository.BrandProductPriceStatsRepository
import com.mycompany.shopping.product.service.BrandStatsService
import com.mycompany.shopping.product.service.ProductService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import com.mycompany.shopping.product.infrastructures.ProductEventSubscriber
import com.mycompany.shopping.product.event.ProductEvent
import org.slf4j.LoggerFactory
import java.time.Instant
import com.mycompany.shopping.product.interfaces.BrandProductPriceStats
import com.mycompany.shopping.product.domain.BrandProductPriceStatsDomain

@Service
class BrandStatsServiceImpl(
    private val statsRepository: BrandProductPriceStatsRepository,
    private val productService: ProductService,
    private val eventSubscriber: ProductEventSubscriber
) : BrandStatsService {
    private val logger = LoggerFactory.getLogger(BrandStatsServiceImpl::class.java)

    init {
        eventSubscriber.subscribe(ProductEvent::class.java)
            .subscribe { event ->
                when (event.eventType) {
                    ProductEvent.EventType.CREATED -> {
                        updateBrandStats(event.product.brandId)
                            .subscribe()
                    }
                    ProductEvent.EventType.UPDATED -> {
                        updateBrandStats(event.product.brandId)
                            .subscribe()
                    }
                    ProductEvent.EventType.DELETED -> {
                        updateBrandStats(event.product.brandId)
                            .subscribe()
                    }
                    ProductEvent.EventType.DELETED_ALL -> {
                        deleteBrandStats(event.product.brandId)
                            .subscribe()
                    }
                }
            }
    }

    override fun getMinTotalPriceBrandStats(): Mono<BrandProductPriceStats> {
        return statsRepository.findByTotalMinPriceBrandStats()
    }

    fun deleteBrandStats(brandId: Long): Mono<Void> {
        return statsRepository.findByBrandId(brandId)
            .flatMap { existingStats ->
                val id = existingStats.id
                if (id != null) {
                    statsRepository.delete(id)
                } else {
                    Mono.empty<Void>()
                }
            }
            .then()
    }

    override fun updateBrandStats(brandId: Long): Mono<Void> {
        return productService.calculateMinPriceSumByCategoryForBrand(brandId)
            .doOnNext { totalMinPrice ->
                logger.info("Updating brand stats for brand {} with total minimum price: {}", brandId, totalMinPrice)
            }
            .flatMap { totalMinPrice ->
                statsRepository.findByBrandId(brandId)
                    .flatMap { existingStats ->
                        val updatedStats = BrandProductPriceStatsDomain(
                            id = existingStats.id,
                            brandId = existingStats.brandId,
                            totalMinPrice = totalMinPrice,
                            createdAt = existingStats.createdAt,
                            updatedAt = Instant.now()
                        )
                        statsRepository.save(updatedStats)
                    }
                    .switchIfEmpty(
                        statsRepository.save(BrandProductPriceStatsDomain(
                            id = null,
                            brandId = brandId,
                            totalMinPrice = totalMinPrice,
                            createdAt = Instant.now(),
                            updatedAt = Instant.now()
                        ))
                    )
                    .then()
            }
            .onErrorResume { error ->
                logger.error("Error updating brand stats for brandId: {}, error: {}", brandId, error.message, error)
                Mono.empty()
            }
    }
}