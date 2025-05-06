package com.mycompany.shopping.brand.dto

import java.time.LocalDateTime

data class BrandResponse(
    val id: Long,
    val name: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) 