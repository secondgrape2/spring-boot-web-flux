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

@Tag(name = "Brand", description = "Brand management APIs")
@RestController
@RequestMapping("/api/v1/brands")
class BrandController(
    private val brandService: BrandService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBrand(@RequestBody request: CreateBrandRequest): Mono<BrandResponse> {
        return brandService.createBrand(request)
    }

    @PutMapping("/{brandId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateBrand(
        @PathVariable brandId: Long,
        @RequestBody request: UpdateBrandRequest
    ): Mono<BrandResponse> {
        return brandService.updateBrand(brandId, request)
    }

    @DeleteMapping("/{brandId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBrand(@PathVariable brandId: Long): Mono<Void> {
        return brandService.deleteBrand(brandId)
    }
} 