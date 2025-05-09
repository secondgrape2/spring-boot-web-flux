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
import com.mycompany.shopping.product.domain.MinMaxPriceProductWithBrandDomain
import com.mycompany.shopping.product.domain.MinMaxPriceProductWithBrandQueryInfo
import com.mycompany.shopping.product.domain.ProductDomain

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

    @Query("""
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
    FROM (
        SELECT
            p.id AS id,
            p.name AS name,
            p.price AS price,
            p.category_id AS category_id,
            p.brand_id AS brand_id,
            p.created_at AS created_at,
            p.updated_at AS updated_at,
            ROW_NUMBER() OVER (PARTITION BY p.category_id ORDER BY p.price ASC, p.id ASC) as rn
        FROM products p
        WHERE p.deleted_at IS NULL AND p.brand_id = :brandId  -- :brandId 조건을 서브쿼리의 WHERE 절로 이동
    ) rp
    JOIN brands b ON rp.brand_id = b.id
    JOIN categories c ON rp.category_id = c.id
    WHERE rp.rn = 1
      AND rp.created_at IS NOT NULL
      AND rp.updated_at IS NOT NULL
      AND b.name IS NOT NULL
      AND b.created_at IS NOT NULL
      AND b.updated_at IS NOT NULL
      -- 아래 조건들은 JOIN 조건 및 서브쿼리의 brandId 필터로 인해 사실상 중복될 수 있으나,
      -- 명시적으로 유지하거나 데이터 정합성을 위해 남겨둘 수 있습니다.
      -- 원본 쿼리에 있었던 조건들을 최대한 유지하는 방향으로 포함했습니다.
      -- AND rp.brand_id IS NOT NULL -- JOIN 및 서브쿼리 필터로 보장됨
      -- AND rp.category_id IS NOT NULL -- JOIN으로 보장됨
      -- AND b.id IS NOT NULL -- JOIN으로 보장됨
""")
    fun findCheapestProductsByBrandId(brandId: Long): Flux<CheapestProductByCategoryQueryResult>

    @Query("""
        (SELECT p.id, p.name, p.price, p.brand_id, p.category_id, p.created_at, p.updated_at,
                b.name AS brand_name, b.created_at AS brand_created_at, b.updated_at AS brand_updated_at
         FROM products p
         JOIN brands b ON p.brand_id = b.id
         WHERE p.category_id = :categoryId AND p.deleted_at IS NULL
         ORDER BY p.price ASC LIMIT 1)
        UNION ALL
        (SELECT p.id, p.name, p.price, p.brand_id, p.category_id, p.created_at, p.updated_at,
                b.name AS brand_name, b.created_at AS brand_created_at, b.updated_at AS brand_updated_at
         FROM products p
         JOIN brands b ON p.brand_id = b.id
         WHERE p.category_id = :categoryId AND p.deleted_at IS NULL
         ORDER BY p.price DESC LIMIT 1)
    """)
    fun findMinMaxPriceProductsWithBrandByCategoryId(categoryId: Long): Flux<MinMaxPriceProductWithBrandQueryResult>

    @Query("""
        SELECT SUM(p.price)
        FROM products p
        WHERE p.brand_id = :brandId AND p.deleted_at IS NULL
        AND NOT EXISTS (
            SELECT 1
            FROM products p2
            WHERE p2.category_id = p.category_id
            AND p2.brand_id = p.brand_id
            AND p2.deleted_at IS NULL
            AND (
                p2.price < p.price OR
                (p2.price = p.price AND p2.id < p.id)
            )
        )
        """)
    fun calculateMinPriceSumByCategoryForBrand(brandId: Long): Mono<Long>
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

    override fun findAll(): Flux<Product> {
        return productR2dbcRepository.findAll()
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

    override fun findCheapestProductsByBrandId(brandId: Long): Flux<ProductWithBrand> {
        return productR2dbcRepository.findCheapestProductsByBrandId(brandId)
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

    override fun findMinMaxPriceProductsWithBrandByCategoryId(categoryId: Long): Mono<MinMaxPriceProductWithBrandDomain> {
        return productR2dbcRepository.findMinMaxPriceProductsWithBrandByCategoryId(categoryId)
            .collectList()
            .map { list ->
                val minPriceProduct = list.first()
                val maxPriceProduct = list.last()
                MinMaxPriceProductWithBrandDomain(
                    categoryId = categoryId,
                    minPriceProduct = MinMaxPriceProductWithBrandQueryInfo(
                        id = minPriceProduct.id,
                        name = minPriceProduct.name,
                        price = minPriceProduct.price,
                        brandId = minPriceProduct.brand_id,
                        createdAt = minPriceProduct.created_at,
                        updatedAt = minPriceProduct.updated_at,
                        brand = BrandDomain(
                            id = minPriceProduct.brand_id,
                            name = minPriceProduct.brand_name,
                            createdAt = minPriceProduct.brand_created_at,
                            updatedAt = minPriceProduct.brand_updated_at,
                        )
                    ),
                    maxPriceProduct = MinMaxPriceProductWithBrandQueryInfo(
                        id = maxPriceProduct.id,
                        name = maxPriceProduct.name,
                        price = maxPriceProduct.price,
                        brandId = maxPriceProduct.brand_id,
                        createdAt = maxPriceProduct.created_at,
                        updatedAt = maxPriceProduct.updated_at,
                        brand = BrandDomain(
                            id = maxPriceProduct.brand_id,
                            name = maxPriceProduct.brand_name,
                            createdAt = maxPriceProduct.brand_created_at,
                            updatedAt = maxPriceProduct.brand_updated_at,
                        )
                    )
                )
            }
    }

    override fun calculateMinPriceSumByCategoryForBrand(brandId: Long): Mono<Long> {
        return productR2dbcRepository.calculateMinPriceSumByCategoryForBrand(brandId)
    }
} 