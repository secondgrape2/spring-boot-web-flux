package com.mycompany.shopping.product.dto

import io.swagger.v3.oas.annotations.media.Schema
import com.mycompany.shopping.product.domain.enums.ProductCategory

@Schema(description = "Response for the price range of products in a category")
data class CategoryPriceRangeResponseDto(
    @Schema(description = "Category name", example = "상의")
    val category: String,

    @Schema(description = "List of lowest priced products")
    val lowestPrice: List<BrandPriceInfoDto>,

    @Schema(description = "List of highest priced products")
    val highestPrice: List<BrandPriceInfoDto>
)

@Schema(description = "Information about a brand and its product price")
data class BrandPriceInfoDto(
    @Schema(description = "Brand name", example = "C")
    val brand: String,

    @Schema(description = "Formatted price", example = "10,000")
    val price: String
) 