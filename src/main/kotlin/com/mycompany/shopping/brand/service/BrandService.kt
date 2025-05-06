package com.mycompany.shopping.brand.service

import com.mycompany.shopping.brand.dto.CreateBrandRequest
import com.mycompany.shopping.brand.dto.UpdateBrandRequest
import com.mycompany.shopping.brand.dto.BrandResponse
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
    fun createBrand(request: CreateBrandRequest): Mono<BrandResponse>
    
    /**
     * Updates an existing brand.
     * @param id The ID of the brand to update
     * @param request The brand update request containing updated brand details
     * @return A Mono emitting the updated brand response
     */
    fun updateBrand(id: Long, request: UpdateBrandRequest): Mono<BrandResponse>
    
    /**
     * Deletes a brand by its ID.
     * @param id The ID of the brand to delete
     * @return A Mono completing when the deletion is done
     */
    fun deleteBrand(id: Long): Mono<Void>
} 