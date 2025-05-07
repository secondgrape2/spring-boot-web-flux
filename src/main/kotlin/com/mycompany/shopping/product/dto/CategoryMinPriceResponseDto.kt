package com.mycompany.shopping.product.dto

import io.swagger.v3.oas.annotations.media.Schema
import com.mycompany.shopping.product.domain.enums.ProductCategory

@Schema(description = "Information about a single category and the lowest priced product in that category")
data class CategoryLowestPriceInfoDto(
    @Schema(description = "Category name", example = "top")
    val category: ProductCategory,

    @Schema(description = "Details of the lowest priced product within the category")
    val lowestProduct: LowestProductDetailsDto
)

data class BrandResponseDto(
    @Schema(description = "Brand name", example = "C")
    val name: String,
)

@Schema(description = "Response for the lowest priced product per category")
data class CategoryMinPriceResponseDto(
    @Schema(description = "List of lowest priced products per category")
    val categories: List<CategoryLowestPriceInfoDto>,

    @Schema(description = "Total sum of all lowest priced products across categories")
    val totalLowestPrice: Int
)

@Schema(description = "Details of the lowest priced product")
data class LowestProductDetailsDto(
    @Schema(description = "Brand information")
    val brand: BrandResponseDto,

    @Schema(description = "Price", example = "10000")
    val price: Int,

    // TODO: Add product details later
)