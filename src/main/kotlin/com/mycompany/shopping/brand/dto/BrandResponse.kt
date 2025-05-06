package com.mycompany.shopping.brand.dto

import java.time.LocalDateTime
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class BrandResponse(
    @Schema(description = "Brand ID")
    @NotBlank(message = "Brand ID is required")
    val id: Long,

    @Schema(description = "Brand name")
    @NotBlank(message = "Brand name is required")
    val name: String,

    @Schema(description = "Created at")
    val createdAt: LocalDateTime,
    
    @Schema(description = "Updated at")
    val updatedAt: LocalDateTime
) 