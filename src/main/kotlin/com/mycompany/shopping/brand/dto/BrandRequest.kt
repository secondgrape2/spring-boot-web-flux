package com.mycompany.shopping.brand.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus

data class CreateBrandRequest(
    @Schema(description = "Brand name")
    @NotBlank(message = "Brand name is required")
    val name: String
)

data class UpdateBrandRequest(
    @Schema(description = "Brand name")
    @NotBlank(message = "Brand name is required")
    val name: String
)