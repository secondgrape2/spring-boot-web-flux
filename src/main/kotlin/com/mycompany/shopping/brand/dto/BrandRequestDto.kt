package com.mycompany.shopping.brand.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import com.mycompany.shopping.brand.interfaces.BrandRequest

data class CreateBrandRequestDto(
    @Schema(description = "Brand name")
    @NotBlank(message = "Brand name is required")
    override val name: String
) : BrandRequest

data class UpdateBrandRequestDto(
    @Schema(description = "Brand name")
    @NotBlank(message = "Brand name is required")
    override val name: String
) : BrandRequest