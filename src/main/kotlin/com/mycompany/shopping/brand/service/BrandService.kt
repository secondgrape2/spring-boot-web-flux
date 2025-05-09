package com.mycompany.shopping.brand.service

import com.mycompany.shopping.brand.dto.BrandResponseDto
import com.mycompany.shopping.brand.dto.CreateBrandRequestDto
import com.mycompany.shopping.brand.dto.UpdateBrandRequestDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Service interface for managing brand operations.
 */
interface BrandService {
    /**
     * Creates a new brand.
     * @param request The brand creation request containing brand details
     * @return A Mono emitting the created brand response
     */
    fun createBrand(dto: CreateBrandRequestDto): Mono<BrandResponseDto>
    
    /**
     * Updates an existing brand.
     * @param id The ID of the brand to update
     * @param request The brand update request containing updated brand details
     * @return A Mono emitting the updated brand response
     */
    fun updateBrand(id: Long, dto: UpdateBrandRequestDto): Mono<BrandResponseDto>
    
    /**
     * Soft deletes a brand by its ID.
     * @param id The ID of the brand to delete
     * @return A Mono completing when the deletion is done
     */
    fun deleteBrand(id: Long): Mono<Void>

    /**
     * Retrieves a brand by its name.
     * @param name The name of the brand to retrieve
     * @return A Mono emitting the brand response
     */
    fun getBrandByName(name: String): Mono<BrandResponseDto>

    /**
     * Retrieves a brand by its ID.
     * @param id The ID of the brand to retrieve
     * @return A Mono emitting the brand response
     */
    fun getBrandById(id: Long): Mono<BrandResponseDto>

    /**
     * Retrieves all brands.
     * @return A Flux emitting all brand response DTOs
     */
    fun getAllBrands(): Flux<BrandResponseDto>
} 