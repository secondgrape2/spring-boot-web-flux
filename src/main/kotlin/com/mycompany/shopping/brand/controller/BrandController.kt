package com.mycompany.shopping.brand.controller

import org.springframework.web.bind.annotation.*
import com.mycompany.shopping.brand.dto.CreateBrandRequestDto
import com.mycompany.shopping.brand.dto.UpdateBrandRequestDto
import com.mycompany.shopping.brand.dto.BrandResponseDto
import com.mycompany.shopping.brand.service.BrandService
import org.springframework.http.HttpStatus
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import jakarta.validation.Valid
import org.slf4j.LoggerFactory

@Tag(name = "Brand", description = "Brand management APIs")
@RestController
@RequestMapping("/api/v1/brands")
@Validated
class BrandController(
    private val brandService: BrandService
) {
    @Operation(
        summary = "Create a new brand",
        description = "Creates a new brand with the provided details"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBrand(
        @Valid @RequestBody dto: CreateBrandRequestDto
    ): Mono<BrandResponseDto> {
        return brandService.createBrand(dto)
    }

    @Operation(
        summary = "Update a brand",
        description = "Updates a brand with the provided details"
    )
    @PutMapping("/{brandId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateBrand(
        @PathVariable brandId: Long,
        @Valid @RequestBody request: UpdateBrandRequestDto
    ): Mono<BrandResponseDto> {
        return brandService.updateBrand(brandId, request)
    }

    @Operation(
        summary = "Delete a brand",
        description = "Deletes a brand with the provided ID"
    )
    @DeleteMapping("/{brandId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBrand(@PathVariable brandId: Long): Mono<Void> {
        return brandService.deleteBrand(brandId)
    }
} 