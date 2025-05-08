package com.mycompany.shopping.product.service.impl

import com.mycompany.shopping.product.dto.*
import com.mycompany.shopping.product.domain.enums.ProductCategory
import com.mycompany.shopping.product.service.ProductService
import com.mycompany.shopping.product.repository.ProductRepository
import com.mycompany.shopping.brand.repository.BrandRepository
import com.mycompany.shopping.category.repository.CategoryRepository
import com.mycompany.shopping.product.domain.ProductDomain
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

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val brandService: BrandService,
    private val categoryService: CategoryService,
    private val productMapper: ProductMapper
) : ProductService {

    private fun findCheapestProductsByCategory(): Flux<ProductWithBrand> {
        return productRepository.findCheapestProductsByCategory()
    }

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
        return findCheapestProductsByCategory()
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
        // TODO: Remove this mock data after implementing the actual data source
        val mockCategories = listOf(
            CategoryPriceInfoDto(ProductCategory.TOP.getLocalizedName("ko"), "10,100"),
            CategoryPriceInfoDto(ProductCategory.OUTER.getLocalizedName("ko"), "5,100"),
            CategoryPriceInfoDto(ProductCategory.BOTTOM.getLocalizedName("ko"), "3,000"),
            CategoryPriceInfoDto(ProductCategory.SHOES.getLocalizedName("ko"), "9,500"),
            CategoryPriceInfoDto(ProductCategory.BAG.getLocalizedName("ko"), "2,500"),
            CategoryPriceInfoDto(ProductCategory.HAT.getLocalizedName("ko"), "1,500"),
            CategoryPriceInfoDto(ProductCategory.SOCKS.getLocalizedName("ko"), "2,400"),
            CategoryPriceInfoDto(ProductCategory.ACCESSORY.getLocalizedName("ko"), "2,000")
        )

        val brandLowestPriceInfo = BrandLowestPriceInfoDto(
            brand = "D",
            categories = mockCategories,
            totalPrice = "36,100"
        )

        return Mono.just(BrandLowestPriceResponseDto(brandLowestPriceInfo))
    }

    override fun getCategoryPriceRange(categoryId: Long): Mono<CategoryPriceRangeResponseDto> {
        return categoryService.getCategoryById(categoryId)
            .switchIfEmpty(Mono.error(CategoryNotFoundException()))
            .flatMap { category -> 
                productRepository.findMinMaxPriceProductsWithBrandByCategoryId(categoryId)
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

    override fun createProduct(request: CreateProductRequestDto): Mono<ProductResponseDto> {
        return Mono.zip(
            brandService.getBrandById(request.brandId),
            categoryService.getCategoryById(request.categoryId)
        )
        .map { tuple -> 
            val brandResponseDto = tuple.t1 
            val categoryResponseDto = tuple.t2 

            val productDomain = ProductDomain(
                id = null,
                name = request.name,
                price = request.price,
                brandId = brandResponseDto.id,
                categoryId = categoryResponseDto.id,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
            productDomain
        }
        .flatMap { productDomain -> productRepository.create(productDomain) }
        .map { product -> productMapper.toResponseDto(product) }
    }

    override fun updateProduct(id: Long, request: UpdateProductRequestDto): Mono<ProductResponseDto> {
        return productRepository.findById(id)
            .switchIfEmpty(Mono.error(ProductNotFoundException()))
            .flatMap { existingProduct ->
                Mono.zip(
                    brandService.getBrandById(request.brandId),
                    categoryService.getCategoryById(request.categoryId)
                ).flatMap { tuple ->
                    val brandResponseDto = tuple.t1
                    val categoryResponseDto = tuple.t2
                    
                    val productDomain = ProductDomain(
                        id = existingProduct.id,
                        name = request.name,
                        price = request.price,
                        brandId = brandResponseDto.id,
                        categoryId = categoryResponseDto.id,
                        createdAt = existingProduct.createdAt,
                        updatedAt = Instant.now()
                    )
                    productRepository.update(productDomain)
                }
            }
            .map { product -> productMapper.toResponseDto(product) }
    }

    override fun deleteProduct(id: Long): Mono<Void> {
        return productRepository.findById(id)
            .switchIfEmpty(Mono.error(ProductNotFoundException()))
            .flatMap { product -> productRepository.softDelete(id) }
    }
} 