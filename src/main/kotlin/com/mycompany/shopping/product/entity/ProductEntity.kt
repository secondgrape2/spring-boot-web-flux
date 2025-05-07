package com.mycompany.shopping.product.entity

import com.mycompany.shopping.product.domain.ProductDomain
import com.mycompany.shopping.product.interfaces.Product
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant
import com.mycompany.shopping.common.exception.InvalidFieldException

@Table("products")
data class ProductEntity(
    @Id
    val id: Long? = null,
    
    @Column("name")
    val name: String,
    
    @Column("price")
    val price: Long,
    
    @Column("brand_id")
    val brandId: Long,
    
    @Column("category_id")
    val categoryId: Long,
    
    @Column("created_at")
    val createdAt: Instant = Instant.now(),
    
    @LastModifiedDate
    @Column("updated_at")
    val updatedAt: Instant = Instant.now(),
    
    @Column("deleted_at")
    val deletedAt: Instant? = null
) {
    fun toDomain(): ProductDomain = ProductDomain(
        id = id ?: throw InvalidFieldException("Product ID cannot be null"),
        name = name,
        price = price,
        brandId = brandId,
        categoryId = categoryId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
} 