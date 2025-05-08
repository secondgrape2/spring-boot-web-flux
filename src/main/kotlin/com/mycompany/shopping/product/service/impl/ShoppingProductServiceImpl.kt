package com.mycompany.shopping.product.service.impl

import com.mycompany.shopping.product.dto.*
import com.mycompany.shopping.product.domain.enums.ProductCategory
import com.mycompany.shopping.brand.BrandNotFoundException
import com.mycompany.shopping.category.exceptions.CategoryNotFoundException
import com.mycompany.shopping.product.exceptions.ProductNotFoundException
import com.mycompany.shopping.product.interfaces.Product
import com.mycompany.shopping.common.exception.CustomErrorCodes
import com.mycompany.shopping.product.mapper.ProductMapper
import com.mycompany.shopping.brand.service.BrandService
import com.mycompany.shopping.category.service.CategoryService
import com.mycompany.shopping.common.util.PriceFormatter
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.Flux
import java.time.Instant
import com.mycompany.shopping.product.interfaces.ProductWithBrand
import com.mycompany.shopping.product.infrastructures.ProductEventPublisher
import com.mycompany.shopping.product.event.ProductEvent
import org.slf4j.LoggerFactory
import com.mycompany.shopping.product.service.BrandStatsService
import com.mycompany.shopping.product.service.ShoppingProductService
import com.mycompany.shopping.product.service.ProductService

@Service
class ShoppingProductServiceImpl(
    private val brandService: BrandService,
    private val categoryService: CategoryService,
    private val productMapper: ProductMapper,
    private val eventPublisher: ProductEventPublisher,
    private val brandStatsService: BrandStatsService,
    private val productService: ProductService
) : ShoppingProductService {
    private val logger = LoggerFactory.getLogger(ProductServiceImpl::class.java)


    internal fun mapToCategoryLowestPriceInfo(product: ProductWithBrand): CategoryLowestPriceInfoDto {
        return CategoryLowestPriceInfoDto(
            category = product.categoryName.getLocalizedName("ko"),
            lowestProduct = LowestProductDetailsDto(
                brand = BrandResponseDto(name = product.brand.name),
                price = PriceFormatter.format(product.price)
            )
        )
    }

    internal fun calculateTotalLowestPrice(categoryLowestPriceInfo: List<CategoryLowestPriceInfoDto>): String {
        val total = categoryLowestPriceInfo.sumOf { it.lowestProduct.price.replace(",", "").toLong() }
        return PriceFormatter.format(total)
    }

    override fun getCategoryMinPricesWithTotalAmount(): Mono<CategoryMinPriceResponseDto> {
        return productService.findCheapestProductsByCategory()
            .map { product -> mapToCategoryLowestPriceInfo(product) }
            .collectList()
            .map { categoryLowestPriceInfo -> 
                CategoryMinPriceResponseDto(
                    categories = categoryLowestPriceInfo,
                    totalLowestPrice = calculateTotalLowestPrice(categoryLowestPriceInfo)
                )
            }
    }

    override fun getBrandWithLowestTotalPrice(): Mono<BrandLowestPriceResponseDto> {
        return brandStatsService.getMinTotalPriceBrandStats()
            .flatMap { brandStats ->
                productService.findCheapestProductsByBrandId(brandStats.brandId)
                    .collectList()
                    .map { products ->
                        BrandLowestPriceInfoDto(
                            brand = products.first().brand.name,
                            categories = products.map { product ->
                                CategoryPriceInfoDto(product.categoryName.getLocalizedName("ko"), PriceFormatter.format(product.price))
                            },
                            totalPrice = PriceFormatter.format(products.sumOf { product -> product.price })
                        )
                    }
            }
            .switchIfEmpty(Mono.error(BrandNotFoundException()))
            .map { brandLowestPriceInfo ->
                BrandLowestPriceResponseDto(brandLowestPriceInfo)
            }

    }

    override fun getCategoryPriceRange(categoryId: Long): Mono<CategoryPriceRangeResponseDto> {
        return categoryService.getCategoryById(categoryId)
            .switchIfEmpty(Mono.error(CategoryNotFoundException()))
            .flatMap { category -> 
                productService.findMinMaxPriceProductsWithBrandByCategoryId(categoryId)
                    .map { minMaxPriceProduct -> 
                        CategoryPriceRangeResponseDto(
                            category = category.name.getLocalizedName("ko"),
                            lowestPrice = listOf(BrandPriceInfoDto(
                                brand = minMaxPriceProduct.minPriceProduct.brand.name,
                                price = PriceFormatter.format(minMaxPriceProduct.minPriceProduct.price)
                            )),
                            highestPrice = listOf(BrandPriceInfoDto(
                                brand = minMaxPriceProduct.maxPriceProduct.brand.name,
                                price = PriceFormatter.format(minMaxPriceProduct.maxPriceProduct.price)
                            ))
                        )
                    }
            }
    }
} 