package com.mycompany.shopping.product.service.impl

import com.mycompany.shopping.product.dto.*
import com.mycompany.shopping.product.domain.enums.ProductCategory
import com.mycompany.shopping.product.service.ProductService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class ProductServiceImpl : ProductService {

    override fun getCategoryMinPricesWithTotalAmount(): Mono<CategoryMinPriceResponse> {
        // Mock data for demonstration purposes
        // TODO: Remove this mock data after implementing the actual data source
        val mockCategories = listOf(
            CategoryLowestPriceInfo(
                category = ProductCategory.TOP,
                lowestProduct = LowestProductDetails(
                    brand = Brand(name = "A"),
                    price = 11200
                )
            ),
            CategoryLowestPriceInfo(
                category = ProductCategory.OUTER,
                lowestProduct = LowestProductDetails(
                    brand = Brand(name = "E"),
                    price = 5000
                )
            ),
            CategoryLowestPriceInfo(
                category = ProductCategory.PANTS,
                lowestProduct = LowestProductDetails(
                    brand = Brand(name = "D"),
                    price = 3000
                )
            ),
            CategoryLowestPriceInfo(
                category = ProductCategory.SNEAKERS,
                lowestProduct = LowestProductDetails(
                    brand = Brand(name = "G"),
                    price = 9000
                )
            ),
            CategoryLowestPriceInfo(
                category = ProductCategory.BAG,
                lowestProduct = LowestProductDetails(
                    brand = Brand(name = "A"),
                    price = 2000
                )
            ),
            CategoryLowestPriceInfo(
                category = ProductCategory.HAT,
                lowestProduct = LowestProductDetails(
                    brand = Brand(name = "D"),
                    price = 1500
                )
            ),
            CategoryLowestPriceInfo(
                category = ProductCategory.SOCKS,
                lowestProduct = LowestProductDetails(
                    brand = Brand(name = "I"),
                    price = 1700
                )
            ),
            CategoryLowestPriceInfo(
                category = ProductCategory.ACCESSORY,
                lowestProduct = LowestProductDetails(
                    brand = Brand(name = "F"),
                    price = 1900
                )
            )
        )

        val totalLowestPrice = mockCategories.sumOf { it.lowestProduct.price }

        return Mono.just(CategoryMinPriceResponse(mockCategories, totalLowestPrice))
    }

    override fun getBrandWithLowestTotalPrice(): Mono<BrandLowestPriceResponse> {
        // TODO: Remove this mock data after implementing the actual data source
        val mockCategories = listOf(
            CategoryPriceInfo(ProductCategory.TOP, "10,100"),
            CategoryPriceInfo(ProductCategory.OUTER, "5,100"),
            CategoryPriceInfo(ProductCategory.PANTS, "3,000"),
            CategoryPriceInfo(ProductCategory.SNEAKERS, "9,500"),
            CategoryPriceInfo(ProductCategory.BAG, "2,500"),
            CategoryPriceInfo(ProductCategory.HAT, "1,500"),
            CategoryPriceInfo(ProductCategory.SOCKS, "2,400"),
            CategoryPriceInfo(ProductCategory.ACCESSORY, "2,000")
        )

        val brandLowestPriceInfo = BrandLowestPriceInfo(
            brand = "D",
            categories = mockCategories,
            totalPrice = "36,100"
        )

        return Mono.just(BrandLowestPriceResponse(brandLowestPriceInfo))
    }

    override fun getCategoryPriceRange(category: ProductCategory): Mono<CategoryPriceRangeResponse> {
        // TODO: Remove this mock data after implementing the actual data source
        val mockLowestPrice = listOf(
            BrandPriceInfo(
                brand = "C",
                price = "10,000"
            )
        )

        val mockHighestPrice = listOf(
            BrandPriceInfo(
                brand = "I",
                price = "11,400"
            )
        )

        return Mono.just(
            CategoryPriceRangeResponse(
                category = category,
                lowestPrice = mockLowestPrice,
                highestPrice = mockHighestPrice
            )
        )
    }

    override fun createProduct(request: CreateProductRequest): Mono<ProductResponse> {
        // TODO: Implement createProduct
        return Mono.just(ProductResponse(
            id = 1, 
            name = request.name, 
            price = request.price, 
            brandId = request.brandId, 
            categoryId = request.categoryId,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        ))
    }

    override fun updateProduct(id: Long, request: UpdateProductRequest): Mono<ProductResponse> {
        // TODO: Implement updateProduct
        return Mono.just(ProductResponse(
            id = id, 
            name = request.name, 
            price = request.price, 
            brandId = request.brandId, 
            categoryId = request.categoryId,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        ))
    }

    override fun deleteProduct(id: Long): Mono<Void> {
        // TODO: Implement deleteProduct
        return Mono.empty()
    }
} 