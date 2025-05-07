package com.mycompany.shopping.brand.entity

import com.mycompany.shopping.brand.interfaces.Brand
import com.mycompany.shopping.brand.domain.BrandDomain
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("brands")
data class BrandEntity(
    @Id
    val id: Long? = null,
    
    @Column("name")
    val name: String,
    
    @Column("created_at")
    val createdAt: Instant = Instant.now(),
    
    @LastModifiedDate
    @Column("updated_at")
    val updatedAt: Instant = Instant.now(),
    
    @Column("deleted_at")
    val deletedAt: Instant? = null
) {
    fun toDomain(): BrandDomain = BrandDomain(
        id = id ?: throw IllegalStateException("Brand ID cannot be null"),
        name = name,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
} 