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
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Instant

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val brandService: BrandService,
    private val categoryService: CategoryService,
    private val productMapper: ProductMapper
) : ProductService {

    override fun getCategoryMinPricesWithTotalAmount(): Mono<CategoryMinPriceResponseDto> {
        // Mock data for demonstration purposes
        // TODO: Remove this mock data after implementing the actual data source
        val mockCategories = listOf(
            CategoryLowestPriceInfoDto(
                category = ProductCategory.TOP,
                lowestProduct = LowestProductDetailsDto(
                    brand = BrandResponseDto(name = "A"),
                    price = 11200
                )
            ),
            CategoryLowestPriceInfoDto(
                category = ProductCategory.OUTER,
                lowestProduct = LowestProductDetailsDto(
                    brand = BrandResponseDto(name = "E"),
                    price = 5000
                )
            ),
            CategoryLowestPriceInfoDto(
                category = ProductCategory.PANTS,
                lowestProduct = LowestProductDetailsDto(
                    brand = BrandResponseDto(name = "D"),
                    price = 3000
                )
            ),
            CategoryLowestPriceInfoDto(
                category = ProductCategory.SNEAKERS,
                lowestProduct = LowestProductDetailsDto(
                    brand = BrandResponseDto(name = "G"),
                    price = 9000
                )
            ),
            CategoryLowestPriceInfoDto(
                category = ProductCategory.BAG,
                lowestProduct = LowestProductDetailsDto(
                    brand = BrandResponseDto(name = "A"),
                    price = 2000
                )
            ),
            CategoryLowestPriceInfoDto(
                category = ProductCategory.HAT,
                lowestProduct = LowestProductDetailsDto(
                    brand = BrandResponseDto(name = "D"),
                    price = 1500
                )
            ),
            CategoryLowestPriceInfoDto(
                category = ProductCategory.SOCKS,
                lowestProduct = LowestProductDetailsDto(
                    brand = BrandResponseDto(name = "I"),
                    price = 1700
                )
            ),
            CategoryLowestPriceInfoDto(
                category = ProductCategory.ACCESSORY,
                lowestProduct = LowestProductDetailsDto(
                    brand = BrandResponseDto(name = "F"),
                    price = 1900
                )
            )
        )

        val totalLowestPrice = mockCategories.sumOf { it.lowestProduct.price }

        return Mono.just(CategoryMinPriceResponseDto(mockCategories, totalLowestPrice))
    }

    override fun getBrandWithLowestTotalPrice(): Mono<BrandLowestPriceResponseDto> {
        // TODO: Remove this mock data after implementing the actual data source
        val mockCategories = listOf(
            CategoryPriceInfoDto(ProductCategory.TOP, "10,100"),
            CategoryPriceInfoDto(ProductCategory.OUTER, "5,100"),
            CategoryPriceInfoDto(ProductCategory.PANTS, "3,000"),
            CategoryPriceInfoDto(ProductCategory.SNEAKERS, "9,500"),
            CategoryPriceInfoDto(ProductCategory.BAG, "2,500"),
            CategoryPriceInfoDto(ProductCategory.HAT, "1,500"),
            CategoryPriceInfoDto(ProductCategory.SOCKS, "2,400"),
            CategoryPriceInfoDto(ProductCategory.ACCESSORY, "2,000")
        )

        val brandLowestPriceInfo = BrandLowestPriceInfoDto(
            brand = "D",
            categories = mockCategories,
            totalPrice = "36,100"
        )

        return Mono.just(BrandLowestPriceResponseDto(brandLowestPriceInfo))
    }

    override fun getCategoryPriceRange(category: ProductCategory): Mono<CategoryPriceRangeResponseDto> {
        // TODO: Remove this mock data after implementing the actual data source
        val mockLowestPrice = listOf(
            BrandPriceInfoDto(
                brand = "C",
                price = "10,000"
            )
        )

        val mockHighestPrice = listOf(
            BrandPriceInfoDto(
                brand = "I",
                price = "11,400"
            )
        )

        return Mono.just(
            CategoryPriceRangeResponseDto(
                category = category,
                lowestPrice = mockLowestPrice,
                highestPrice = mockHighestPrice
            )
        )
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
        .flatMap { productDomain -> 
            productRepository.create(productDomain)
        }
        .map { product -> 
                productMapper.toResponse(product)
            }
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
            .map { productMapper.toResponse(it) }
    }

    override fun deleteProduct(id: Long): Mono<Void> {
        return productRepository.findById(id)
            .switchIfEmpty(Mono.error(ProductNotFoundException()))
            .flatMap { productRepository.softDelete(id) }
    }
} 