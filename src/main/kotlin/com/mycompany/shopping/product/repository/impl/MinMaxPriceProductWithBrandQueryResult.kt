package com.mycompany.shopping.product.repository.impl

import java.time.Instant

data class MinMaxPriceProductWithBrandQueryResult(
    val id: Long,
    val name: String,
    val price: Long,
    val brand_id: Long,
    val category_id: Long,
    val created_at: Instant,
    val updated_at: Instant,
    val brand_name: String,
    val brand_created_at: Instant,
    val brand_updated_at: Instant
)