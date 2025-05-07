package com.mycompany.shopping.product.repository.impl

import com.mycompany.shopping.product.interfaces.Product
import com.mycompany.shopping.product.repository.ProductRepository
import com.mycompany.shopping.product.entity.ProductEntity
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import java.time.Instant

@Repository
interface ProductR2dbcRepository : ReactiveCrudRepository<ProductEntity, Long> {
    @Modifying
    @Query("UPDATE products SET deleted_at = :deletedAt, updated_at = :updatedAt WHERE id = :id AND deleted_at IS NULL")
    fun markAsDeleted(id: Long, deletedAt: Instant, updatedAt: Instant): Mono<Integer>
}

@Repository("productRepository")
class ProductRepositoryR2dbcImpl(
    private val productR2dbcRepository: ProductR2dbcRepository
) : ProductRepository {
    override fun create(product: Product): Mono<Product> {
        val productEntity = ProductEntity(
            name = product.name,
            price = product.price,
            brandId = product.brandId,
            categoryId = product.categoryId,
        )
        return productR2dbcRepository.save(productEntity)
            .map { it.toDomain() }
    }

    override fun update(product: Product): Mono<Product> {
        val id = product.id ?: return Mono.error(IllegalArgumentException("Product ID cannot be null"))
        return productR2dbcRepository.findById(id)
            .filter { it.deletedAt == null }
            .flatMap { productEntity ->
                val updatedEntity = productEntity.copy(
                    name = product.name,
                    price = product.price,
                    brandId = product.brandId,
                    categoryId = product.categoryId,
                    updatedAt = Instant.now()
                )
                productR2dbcRepository.save(updatedEntity)
            }.map { it.toDomain() }
    }

    override fun findById(id: Long): Mono<Product> {
        return productR2dbcRepository.findById(id)
            .filter { it.deletedAt == null }
            .map { it.toDomain() }
    }

    override fun softDelete(id: Long): Mono<Void> {
        val now = Instant.now()
        return productR2dbcRepository.markAsDeleted(id, now, now)
            .then()
    }
} 