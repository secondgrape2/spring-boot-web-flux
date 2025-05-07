package com.mycompany.shopping.category.entity

import com.mycompany.shopping.category.domain.CategoryDomain
import com.mycompany.shopping.category.interfaces.Category
import com.mycompany.shopping.product.domain.enums.ProductCategory
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("categories")
data class CategoryEntity(
    @Id
    val id: Long? = null,
    
    @Column("name")
    val name: ProductCategory,
    
    @Column("created_at")
    val createdAt: Instant = Instant.now(),
    
    @LastModifiedDate
    @Column("updated_at")
    val updatedAt: Instant = Instant.now(),
    
    @Column("deleted_at")
    val deletedAt: Instant? = null
) {
    fun toDomain(): CategoryDomain = CategoryDomain(
        id = id ?: throw IllegalStateException("Category ID cannot be null"),
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
} 