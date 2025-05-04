package com.mycompany.shopping.product.service.impl

import com.mycompany.shopping.product.dto.Brand
import com.mycompany.shopping.product.dto.CategoryLowestPriceInfo
import com.mycompany.shopping.product.dto.CategoryMinPriceResponse
import com.mycompany.shopping.product.dto.LowestProductDetails
import com.mycompany.shopping.product.service.ProductService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ProductServiceImpl : ProductService {

    override fun getCategoryMinPricesWithTotalAmount(): Mono<CategoryMinPriceResponse> {
        // Mock data for demonstration purposes
        // TODO: Remove this mock data after implementing the actual data source
        val mockCategories = listOf(
            CategoryLowestPriceInfo(
                category = "top",
                lowestProduct = LowestProductDetails(
                    brand = Brand(name = "A"),
                    price = 11200
                )
            ),
            CategoryLowestPriceInfo(
                category = "outer",
                lowestProduct = LowestProductDetails(
                    brand = Brand(name = "E"),
                    price = 5000
                )
            ),
            CategoryLowestPriceInfo(
                category = "pants",
                lowestProduct = LowestProductDetails(
                    brand = Brand(name = "D"),
                    price = 3000
                )
            ),
            CategoryLowestPriceInfo(
                category = "sneakers",
                lowestProduct = LowestProductDetails(
                    brand = Brand(name = "G"),
                    price = 9000
                )
            ),
            CategoryLowestPriceInfo(
                category = "bag",
                lowestProduct = LowestProductDetails(
                    brand = Brand(name = "A"),
                    price = 2000
                )
            ),
            CategoryLowestPriceInfo(
                category = "hat",
                lowestProduct = LowestProductDetails(
                    brand = Brand(name = "D"),
                    price = 1500
                )
            ),
            CategoryLowestPriceInfo(
                category = "socks",
                lowestProduct = LowestProductDetails(
                    brand = Brand(name = "I"),
                    price = 1700
                )
            ),
            CategoryLowestPriceInfo(
                category = "accessory",
                lowestProduct = LowestProductDetails(
                    brand = Brand(name = "F"),
                    price = 1900
                )
            )
        )

        val totalLowestPrice = mockCategories.sumOf { it.lowestProduct.price }

        return Mono.just(CategoryMinPriceResponse(mockCategories, totalLowestPrice))
    }
} 