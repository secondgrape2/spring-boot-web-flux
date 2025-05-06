package com.mycompany.shopping.brand.controller

import com.mycompany.shopping.brand.dto.CreateBrandRequest
import com.mycompany.shopping.brand.dto.UpdateBrandRequest
import com.mycompany.shopping.brand.dto.BrandResponse
import com.mycompany.shopping.brand.service.BrandService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.parameters.RequestBody

@Tag(name = "Brand", description = "Brand management APIs")
@RestController
@RequestMapping("/api/v1/brands")
class BrandController(
    private val brandService: BrandService
) {
    @Operation(
        summary = "Create a new brand",
        description = "Creates a new brand with the provided details"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBrand(@RequestBody request: CreateBrandRequest): Mono<BrandResponse> {
        return brandService.createBrand(request)
    }

    @Operation(
        summary = "Update a brand",
        description = "Updates a brand with the provided details"
    )
    @PutMapping("/{brandId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateBrand(
        @PathVariable brandId: Long,
        @RequestBody request: UpdateBrandRequest
    ): Mono<BrandResponse> {
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