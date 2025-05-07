package com.mycompany.shopping.product.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import io.swagger.v3.oas.annotations.media.Schema

data class CreateProductRequestDto(
    @Schema(description = "Product name")
    @NotBlank(message = "Product name is required")
    val name: String,

    @Schema(description = "Product price")
    @NotNull(message = "Product price is required")
    @Positive(message = "Price must be greater than 0")
    val price: Long,

    @Schema(description = "Brand Id")
    @NotNull(message = "Brand Id is required")
    val brandId: Long,

    @Schema(description = "Category Id")
    @NotNull(message = "Category Id is required")
    val categoryId: Long
)

data class UpdateProductRequestDto(
    @Schema(description = "Product name")
    @NotBlank(message = "Product name is required")
    val name: String,

    @Schema(description = "Product price")
    @NotNull(message = "Product price is required")
    @Positive(message = "Price must be greater than 0")
    val price: Long,

    @Schema(description = "Brand Id")
    @NotNull(message = "Brand Id is required")
    val brandId: Long,

    @Schema(description = "Category Id")
    @NotNull(message = "Category Id is required")
    val categoryId: Long
) 