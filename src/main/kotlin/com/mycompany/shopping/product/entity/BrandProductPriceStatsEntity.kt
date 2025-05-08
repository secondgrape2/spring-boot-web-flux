package com.mycompany.shopping.product.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant
import com.mycompany.shopping.product.domain.BrandProductPriceStatsDomain

/**
 * Entity class representing brand product total min price statistics in the database.
 * Maps to the brand_product_price_stats table.
 */
@Table("brand_product_price_stats")
data class BrandProductPriceStatsEntity(
    @Id
    val id: Long? = null,
    
    @Column("brand_id")
    val brandId: Long,
    
    @Column("total_min_price")
    val totalMinPrice: Long,
    
    @Column("created_at")
    val createdAt: Instant = Instant.now(),
    
    @Column("updated_at")
    val updatedAt: Instant = Instant.now()
) {
    fun toDomain(): BrandProductPriceStatsDomain = BrandProductPriceStatsDomain(
        id = id,
        brandId = brandId,
        totalMinPrice = totalMinPrice,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}