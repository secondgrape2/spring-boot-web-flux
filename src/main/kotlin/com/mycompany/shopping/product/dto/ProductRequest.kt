package com.mycompany.shopping.product.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import io.swagger.v3.oas.annotations.media.Schema

data class CreateProductRequest(
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
    val categoryId: Long
)

data class UpdateProductRequest(
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
    val categoryId: Long
) 