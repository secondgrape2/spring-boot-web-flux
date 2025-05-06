package com.mycompany.shopping.product.dto

import java.time.LocalDateTime
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class ProductResponse(
    @Schema(description = "Product ID")
    @NotBlank(message = "Product ID is required")
    val id: Long,

    @Schema(description = "Product name")
    @NotBlank(message = "Product name is required")
    val name: String,

    @Schema(description = "Product price")
    @NotNull(message = "Product price is required")
    @Positive(message = "Price must be greater than 0")
    val price: Long,

    @Schema(description = "Brand ID")
    @NotNull(message = "Brand ID is required")
    @Positive(message = "Brand ID must be greater than 0")
    val brandId: Long,

    @Schema(description = "Category ID")
    @NotNull(message = "Category ID is required")
    @Positive(message = "Category ID must be greater than 0")
    val categoryId: Long,

    @Schema(description = "Created at")
    @NotNull(message = "Created at is required")
    val createdAt: LocalDateTime,

    @Schema(description = "Updated at")
    @NotNull(message = "Updated at is required")
    val updatedAt: LocalDateTime
) 