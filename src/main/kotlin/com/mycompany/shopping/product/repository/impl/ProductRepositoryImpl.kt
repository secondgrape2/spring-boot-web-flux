package com.mycompany.shopping.product.repository.impl

import com.mycompany.shopping.product.interfaces.Product
import com.mycompany.shopping.product.repository.ProductRepository
import com.mycompany.shopping.product.entity.ProductEntity
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import reactor.core.publisher.Flux
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import java.time.Instant
import com.mycompany.shopping.product.interfaces.ProductWithBrand
import com.mycompany.shopping.product.domain.ProductWithBrandDomain
import com.mycompany.shopping.product.domain.enums.ProductCategory
import com.mycompany.shopping.brand.domain.BrandDomain
import com.mycompany.shopping.product.repository.impl.CheapestProductByCategoryQueryResult

@Repository
interface ProductR2dbcRepository : ReactiveCrudRepository<ProductEntity, Long> {
    @Modifying
    @Query("UPDATE products SET deleted_at = :deletedAt, updated_at = :updatedAt WHERE id = :id AND deleted_at IS NULL")
    fun markAsDeleted(id: Long, deletedAt: Instant, updatedAt: Instant): Mono<Integer>

    @Query("""
        WITH RankedProducts AS (
            SELECT
                p.id AS id,
                p.name AS name,
                p.price AS price,
                p.category_id AS category_id,
                p.brand_id AS brand_id,
                p.created_at AS created_at,
                p.updated_at AS updated_at,
                p.deleted_at AS deleted_at,
                ROW_NUMBER() OVER (PARTITION BY p.category_id ORDER BY p.price ASC, p.id ASC) as rn
            FROM products p
            WHERE p.deleted_at IS NULL
        )
        SELECT
            rp.id,
            rp.name,
            rp.price,
            rp.brand_id,
            rp.category_id,
            rp.created_at,
            rp.updated_at,
            b.name AS brand_name,
            b.created_at AS brand_created_at,
            b.updated_at AS brand_updated_at,
            c.name AS category_name
        FROM RankedProducts rp
        JOIN brands b ON rp.brand_id = b.id
        JOIN categories c ON rp.category_id = c.id
        WHERE rp.rn = 1
          AND rp.brand_id IS NOT NULL
          AND rp.category_id IS NOT NULL
          AND rp.created_at IS NOT NULL
          AND rp.updated_at IS NOT NULL
          AND b.id IS NOT NULL
          AND b.name IS NOT NULL
          AND b.created_at IS NOT NULL
          AND b.updated_at IS NOT NULL;
    """)
    fun findCheapestProductsByCategory(): Flux<CheapestProductByCategoryQueryResult>
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

    override fun findCheapestProductsByCategory(): Flux<ProductWithBrand> {
        return productR2dbcRepository.findCheapestProductsByCategory()
            .map { it -> 
                ProductWithBrandDomain(
                    id = it.id,
                    name = it.name,
                    price = it.price,
                    brandId = it.brand_id,
                    categoryId = it.category_id,
                    createdAt = it.created_at,
                    updatedAt = it.updated_at,
                    brand = BrandDomain(
                        id = it.brand_id,
                        name = it.brand_name,
                        createdAt = it.brand_created_at,
                        updatedAt = it.brand_updated_at,
                    ),
                    categoryName = ProductCategory.valueOf(it.category_name)
                )
            }
    }
} 