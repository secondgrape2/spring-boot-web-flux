package com.mycompany.shopping.brand.repository.impl

import com.mycompany.shopping.brand.interfaces.Brand
import com.mycompany.shopping.brand.interfaces.BrandRequest
import com.mycompany.shopping.brand.repository.BrandRepository
import com.mycompany.shopping.brand.entity.BrandEntity
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import java.time.Instant


@Repository
interface BrandR2dbcRepository : ReactiveCrudRepository<BrandEntity, Long> {
    @Modifying
    @Query("UPDATE brands SET deleted_at = :deletedAt, updated_at = :updatedAt WHERE id = :id AND deleted_at IS NULL")
    fun markAsDeleted(id: Long, deletedAt: Instant, updatedAt: Instant): Mono<Integer>

    @Query("SELECT * FROM brands WHERE name = :name AND deleted_at IS NULL")
    fun findByName(name: String): Mono<BrandEntity>
}

@Repository("brandRepository")
class BrandRepositoryR2dbcImpl(
    private val brandR2dbcRepository: BrandR2dbcRepository
) : BrandRepository {
    override fun create(brand: BrandRequest): Mono<Brand> {
        val brandEntity = BrandEntity(
            name = brand.name,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
        val savedBrand = brandR2dbcRepository.save(brandEntity)

        return savedBrand.map { it.toDomain() }
    }
    override fun update(brand: Brand): Mono<Brand> {
        val id = brand.id ?: return Mono.error(IllegalArgumentException("Brand ID cannot be null"))
        return brandR2dbcRepository.findById(id)
                .filter { it.deletedAt == null }
                .flatMap { brandEntity ->
                    val updatedEntity = brandEntity.copy(
                        name = brand.name,
                        updatedAt = Instant.now()
                    )
                    brandR2dbcRepository.save(updatedEntity)
                }.map { it.toDomain() }
    }

    override fun findById(id: Long): Mono<Brand> {
        return brandR2dbcRepository.findById(id)
                .filter { it.deletedAt == null }
                .map { it.toDomain() }
    }

    override fun findByName(name: String): Mono<Brand> {
        return brandR2dbcRepository.findByName(name)
                .filter { it.deletedAt == null }
                .map { it.toDomain() }
    }

    override fun softDelete(id: Long): Mono<Void> {
        val now = Instant.now()
        return brandR2dbcRepository.markAsDeleted(id, now, now)
                .then()
    }

    override fun findAll(): Flux<Brand> {
        return brandR2dbcRepository.findAll()
            .filter { it.deletedAt == null }
            .map { it.toDomain() }
    }
}