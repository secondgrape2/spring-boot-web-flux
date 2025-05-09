package com.mycompany.shopping.brand.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Brand list response with pagination metadata")
data class BrandListData(
    @Schema(description = "List of brands")
    val data: List<BrandResponseDto>,
    @Schema(description = "Total number of brands")
    val totalCount: Long,
    @Schema(description = "Total number of pages")
    val totalPages: Int,
    @Schema(description = "Current page number")
    val currentPage: Int,
    @Schema(description = "Page size")
    val pageSize: Int
) 