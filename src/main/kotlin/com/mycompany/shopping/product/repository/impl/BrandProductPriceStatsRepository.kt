package com.mycompany.shopping.product.repository.impl

import com.mycompany.shopping.product.entity.BrandProductPriceStatsEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import reactor.core.publisher.Mono
import org.springframework.stereotype.Repository
import com.mycompany.shopping.product.repository.BrandProductPriceStatsRepository
import com.mycompany.shopping.product.interfaces.BrandProductPriceStats

@Repository
interface BrandProductPriceStatsR2dbcRepository : ReactiveCrudRepository<BrandProductPriceStatsEntity, Long> {
    /**
     * Find brand product total min price stats by brand ID
     * @param brandId the brand ID to search for
     * @return the brand product total min price stats entity
     */
    @Query("SELECT * FROM brand_product_price_stats WHERE brand_id = :brandId")
    fun findByBrandId(brandId: Long): Mono<BrandProductPriceStatsEntity>
} 

@Repository
class BrandProductPriceStatsRepositoryR2dbcImpl(
    private val repository: BrandProductPriceStatsR2dbcRepository
) : BrandProductPriceStatsRepository {
    override fun findByBrandId(brandId: Long): Mono<BrandProductPriceStats> {
        return repository.findByBrandId(brandId)
            .map { it.toDomain() }
    }

    override fun save(brandProductPriceStats: BrandProductPriceStats): Mono<BrandProductPriceStats> {
        val brandProductPriceStatsEntity = BrandProductPriceStatsEntity(
            brandId = brandProductPriceStats.brandId,
            totalMinPrice = brandProductPriceStats.totalMinPrice,
            createdAt = brandProductPriceStats.createdAt,
            updatedAt = brandProductPriceStats.updatedAt
        )
        return repository.save(brandProductPriceStatsEntity)
            .map { it.toDomain() }
    }
}
