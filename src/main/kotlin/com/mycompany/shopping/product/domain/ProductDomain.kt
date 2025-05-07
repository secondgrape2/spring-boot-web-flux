package com.mycompany.shopping.product.domain

import com.mycompany.shopping.product.interfaces.Product
import com.mycompany.shopping.product.interfaces.ProductWithBrand
import com.mycompany.shopping.brand.interfaces.Brand
import com.mycompany.shopping.product.domain.enums.ProductCategory
import java.time.Instant

data class ProductDomain(
    override val id: Long?,
    override val name: String,
    override val price: Long,
    override val brandId: Long,
    override val categoryId: Long,
    override val createdAt: Instant,
    override val updatedAt: Instant
) : Product 

data class ProductWithBrandDomain(
    override val id: Long?,
    override val name: String,
    override val price: Long,
    override val brandId: Long,
    override val categoryId: Long,
    override val createdAt: Instant,
    override val updatedAt: Instant,
    override val brand: Brand,
    override val categoryName: ProductCategory
) : ProductWithBrand

data class MinMaxPriceProductWithBrandQueryInfo(
    val id: Long,
    val name: String,
    val price: Long,
    val brandId: Long,
    val createdAt: Instant,
    val updatedAt: Instant,
    val brand: Brand,
)

data class MinMaxPriceProductWithBrandDomain(
    val categoryId: Long,
    val minPriceProduct: MinMaxPriceProductWithBrandQueryInfo,
    val maxPriceProduct: MinMaxPriceProductWithBrandQueryInfo
)