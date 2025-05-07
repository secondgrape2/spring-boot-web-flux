package com.mycompany.shopping.product.dto

import io.swagger.v3.oas.annotations.media.Schema
import com.mycompany.shopping.product.domain.enums.ProductCategory

@Schema(description = "Response for the lowest priced brand across all categories")
data class BrandLowestPriceResponseDto(
    @Schema(description = "Information about the lowest priced brand")
    val lowestPriceInfo: BrandLowestPriceInfoDto
)

@Schema(description = "Information about a product in a specific category")
data class CategoryPriceInfoDto(
    @Schema(description = "Category name", example = "top")
    val category: ProductCategory,

    @Schema(description = "Formatted price", example = "10,100")
    val price: String
)

@Schema(description = "Information about the lowest priced brand and its products")
data class BrandLowestPriceInfoDto(
    @Schema(description = "Brand name", example = "D")
    val brand: String,

    @Schema(description = "List of products by category with their prices")
    val categories: List<CategoryPriceInfoDto>,

    @Schema(description = "Total price for all products", example = "36,100")
    val totalPrice: String
)

