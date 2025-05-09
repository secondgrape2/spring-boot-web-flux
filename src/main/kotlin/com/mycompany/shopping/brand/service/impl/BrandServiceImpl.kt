package com.mycompany.shopping.brand.service.impl

import com.mycompany.shopping.brand.interfaces.Brand
import com.mycompany.shopping.brand.interfaces.BrandRequest
import com.mycompany.shopping.brand.domain.BrandDomain
import com.mycompany.shopping.brand.dto.BrandResponseDto
import com.mycompany.shopping.brand.dto.CreateBrandRequestDto
import com.mycompany.shopping.brand.dto.UpdateBrandRequestDto
import com.mycompany.shopping.brand.repository.BrandRepository
import com.mycompany.shopping.brand.service.BrandService
import com.mycompany.shopping.common.exception.CustomErrorCodes
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Mono
import java.time.Instant
import com.mycompany.shopping.common.exception.InvalidFieldException
import reactor.core.publisher.Flux

@Service
class BrandServiceImpl(
    private val brandRepository: BrandRepository
) : BrandService {

    override fun createBrand(dto: CreateBrandRequestDto): Mono<BrandResponseDto> {
        return brandRepository.create(dto)
            .map { mapToBrandResponse(it) }
    }

    override fun updateBrand(id: Long, dto: UpdateBrandRequestDto): Mono<BrandResponseDto> {
        val brandDomain = BrandDomain(
            id = id,
            name = dto.name,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
        return brandRepository.update(brandDomain)
            .map { mapToBrandResponse(it) }
    }

    override fun deleteBrand(id: Long): Mono<Void> {
        return brandRepository.softDelete(id)
    }

    override fun getBrandByName(name: String): Mono<BrandResponseDto> {
        return brandRepository.findByName(name)
            .map { mapToBrandResponse(it) }
    }
    
    override fun getBrandById(id: Long): Mono<BrandResponseDto> {
        return brandRepository.findById(id)
            .map { mapToBrandResponse(it) }
    }

    override fun getAllBrands(): Flux<BrandResponseDto> {
        return brandRepository.findAll()
            .map { mapToBrandResponse(it) }
    }

    private fun mapToBrandResponse(brand: Brand): BrandResponseDto {
        val id = brand.id ?: throw InvalidFieldException("Brand ID is null")
        return BrandResponseDto(
            id = id,
            name = brand.name,
            createdAt = brand.createdAt,
            updatedAt = brand.updatedAt
        )
    }
} 