package com.mycompany.shopping.category.dto

import java.time.Instant
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import com.mycompany.shopping.product.domain.enums.ProductCategory

data class CategoryResponseDto(
    @Schema(description = "Category ID")
    @NotBlank(message = "Category ID is required")
    val id: Long,

    @Schema(description = "Category name")
    @NotBlank(message = "Category name is required")
    val name: ProductCategory,

    @Schema(description = "Created at")
    val createdAt: Instant,
    
    @Schema(description = "Updated at")
    val updatedAt: Instant
) 