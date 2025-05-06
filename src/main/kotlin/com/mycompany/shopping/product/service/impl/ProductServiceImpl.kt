package com.mycompany.shopping.product.service.impl

import com.mycompany.shopping.product.dto.*
import com.mycompany.shopping.product.domain.enums.Category
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
                category = Category.TOP,
                lowestProduct = LowestProductDetails(
                    brand = Brand(name = "A"),
                    price = 11200
                )
            ),
            CategoryLowestPriceInfo(
                category = Category.OUTER,
                lowestProduct = LowestProductDetails(
                    brand = Brand(name = "E"),
                    price = 5000
                )
            ),
            CategoryLowestPriceInfo(
                category = Category.PANTS,
                lowestProduct = LowestProductDetails(
                    brand = Brand(name = "D"),
                    price = 3000
                )
            ),
            CategoryLowestPriceInfo(
                category = Category.SNEAKERS,
                lowestProduct = LowestProductDetails(
                    brand = Brand(name = "G"),
                    price = 9000
                )
            ),
            CategoryLowestPriceInfo(
                category = Category.BAG,
                lowestProduct = LowestProductDetails(
                    brand = Brand(name = "A"),
                    price = 2000
                )
            ),
            CategoryLowestPriceInfo(
                category = Category.HAT,
                lowestProduct = LowestProductDetails(
                    brand = Brand(name = "D"),
                    price = 1500
                )
            ),
            CategoryLowestPriceInfo(
                category = Category.SOCKS,
                lowestProduct = LowestProductDetails(
                    brand = Brand(name = "I"),
                    price = 1700
                )
            ),
            CategoryLowestPriceInfo(
                category = Category.ACCESSORY,
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
            CategoryPriceInfo(Category.TOP, "10,100"),
            CategoryPriceInfo(Category.OUTER, "5,100"),
            CategoryPriceInfo(Category.PANTS, "3,000"),
            CategoryPriceInfo(Category.SNEAKERS, "9,500"),
            CategoryPriceInfo(Category.BAG, "2,500"),
            CategoryPriceInfo(Category.HAT, "1,500"),
            CategoryPriceInfo(Category.SOCKS, "2,400"),
            CategoryPriceInfo(Category.ACCESSORY, "2,000")
        )

        val brandLowestPriceInfo = BrandLowestPriceInfo(
            brand = "D",
            categories = mockCategories,
            totalPrice = "36,100"
        )

        return Mono.just(BrandLowestPriceResponse(brandLowestPriceInfo))
    }

    override fun getCategoryPriceRange(category: Category): Mono<CategoryPriceRangeResponse> {
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
} 