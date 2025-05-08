package com.mycompany.shopping.product.service.impl

import com.mycompany.shopping.product.repository.BrandProductPriceStatsRepository
import com.mycompany.shopping.product.service.BrandStatsService
import com.mycompany.shopping.product.service.ProductService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import com.mycompany.shopping.product.infrastructures.ProductEventSubscriber
import com.mycompany.shopping.product.event.ProductEvent
import org.slf4j.LoggerFactory
import com.mycompany.shopping.product.domain.BrandProductPriceStatsDomain
import java.time.Instant
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
                updateBrandStats(event.product.brandId)
                .subscribe()
            }
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