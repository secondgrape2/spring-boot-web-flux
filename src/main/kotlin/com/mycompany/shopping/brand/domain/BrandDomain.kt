package com.mycompany.shopping.brand.domain

import com.mycompany.shopping.brand.interfaces.Brand
import java.time.Instant

data class BrandDomain(
    override val id: Long,
    override val name: String,
    override val createdAt: Instant,
    override val updatedAt: Instant
) : Brand