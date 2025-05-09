package com.mycompany.shopping.product.dto

data class ProductListData(
    val data: List<ProductResponseDto>,
    val totalCount: Long,
    val totalPages: Int,
    val currentPage: Int,
    val pageSize: Int
) 