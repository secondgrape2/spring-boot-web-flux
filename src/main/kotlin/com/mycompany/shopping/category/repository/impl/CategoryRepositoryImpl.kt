package com.mycompany.shopping.category.repository.impl

import com.mycompany.shopping.category.interfaces.Category
import com.mycompany.shopping.category.repository.CategoryRepository
import com.mycompany.shopping.category.entity.CategoryEntity
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.data.r2dbc.repository.Query

@Repository
interface CategoryR2dbcRepository : ReactiveCrudRepository<CategoryEntity, Long> {
    @Query("SELECT * FROM categories WHERE name = :name AND deleted_at IS NULL")
    fun findByName(name: String): Mono<CategoryEntity>
}

@Repository("categoryRepository")
class CategoryRepositoryR2dbcImpl(
    private val categoryR2dbcRepository: CategoryR2dbcRepository
) : CategoryRepository {
    override fun findAll(): Flux<Category> {
        return categoryR2dbcRepository.findAll()
            .filter { it.deletedAt == null }
            .map { it.toDomain() }
    }

    override fun findByName(name: String): Mono<Category> {
        return categoryR2dbcRepository.findByName(name)
            .map { it.toDomain() }
    }
} 