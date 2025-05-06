package com.mycompany.shopping.brand.service.impl

import com.mycompany.shopping.brand.dto.BrandResponse
import com.mycompany.shopping.brand.dto.CreateBrandRequest
import com.mycompany.shopping.brand.dto.UpdateBrandRequest
import com.mycompany.shopping.brand.service.BrandService
import org.springframework.stereotype.Service
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class BrandServiceImpl() : BrandService {

    override fun createBrand(request: CreateBrandRequest): Mono<BrandResponse> {
        // TODO: Implement createBrand
        return Mono.just(
            BrandResponse(
                id = 1L,
                name = request.name,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        )
    }

    override fun updateBrand(id: Long, request: UpdateBrandRequest): Mono<BrandResponse> {
        // TODO: Implement updateBrand
        return Mono.just(
            BrandResponse(
                id = id,
                name = request.name,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
        )
    }

    override fun deleteBrand(id: Long): Mono<Void> {
        // TODO: Implement deleteBrand
        return Mono.empty()
    }
} 