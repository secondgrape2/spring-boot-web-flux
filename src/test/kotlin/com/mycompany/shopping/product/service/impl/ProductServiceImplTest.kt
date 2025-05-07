package com.mycompany.shopping.product.service.impl

import com.mycompany.shopping.product.dto.*
import com.mycompany.shopping.product.domain.enums.ProductCategory
import com.mycompany.shopping.product.interfaces.ProductWithBrand
import com.mycompany.shopping.brand.domain.BrandDomain
import com.mycompany.shopping.product.repository.ProductRepository
import com.mycompany.shopping.brand.service.BrandService
import com.mycompany.shopping.category.service.CategoryService
import com.mycompany.shopping.product.mapper.ProductMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import java.math.BigDecimal
import java.time.Instant
import com.mycompany.shopping.product.domain.ProductWithBrandDomain


@ExtendWith(MockitoExtension::class)
class ProductServiceImplTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    @Mock
    private lateinit var brandService: BrandService

    @Mock
    private lateinit var categoryService: CategoryService

    @Mock
    private lateinit var productMapper: ProductMapper

    @InjectMocks
    private lateinit var productService: ProductServiceImpl
    @Test
    fun `mapToCategoryLowestPriceInfo should correctly map ProductWithBrand to CategoryLowestPriceInfoDto`() {
        // given
        val brand = BrandDomain(id = 1L, name = "Test Brand", createdAt = Instant.now(), updatedAt = Instant.now())
        val product = ProductWithBrandDomain(
            id = 1L,
            name = "Test Product",
            price = 10000,
            brandId = 1L,
            categoryId = 1L,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            brand = brand,
            categoryName = ProductCategory.TOP
        )

        // when
        val result = productService.mapToCategoryLowestPriceInfo(product)

        // then
        assert(result.category == ProductCategory.TOP)
        assert(result.lowestProduct.brand.name == "Test Brand")
        assert(result.lowestProduct.price == 10000L)
    }

    @Test
    fun `calculateTotalLowestPrice should correctly sum all prices`() {
        // given
        val categoryLowestPriceInfo = listOf(
            CategoryLowestPriceInfoDto(
                category = ProductCategory.TOP,
                lowestProduct = LowestProductDetailsDto(
                    brand = BrandResponseDto(name = "Brand1"),
                    price = 10000
                )
            ),
            CategoryLowestPriceInfoDto(
                category = ProductCategory.BOTTOM,
                lowestProduct = LowestProductDetailsDto(
                    brand = BrandResponseDto(name = "Brand2"),
                    price = 20000
                )
            )
        )

        // when
        val result = productService.calculateTotalLowestPrice(categoryLowestPriceInfo)

        // then
        assert(result == 30000L)
    }

    @Test
    fun `getCategoryMinPricesWithTotalAmount should return correct response`() {
        // given
        val brand1 = BrandDomain(id = 1L, name = "Brand1", createdAt = Instant.now(), updatedAt = Instant.now())
        val brand2 = BrandDomain(id = 2L, name = "Brand2", createdAt = Instant.now(), updatedAt = Instant.now())

        val product1 = ProductWithBrandDomain(
            id = 1L,
            name = "Test Product 1",
            price = 10000,
            brandId = 1L,
            categoryId = 1L,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            brand = brand1,
            categoryName = ProductCategory.TOP
        )

        val product2 = ProductWithBrandDomain(
            id = 2L,
            name = "Test Product 2",
            price = 20000,
            brandId = 2L,
            categoryId = 2L,
            createdAt = Instant.now(),
            updatedAt = Instant.now(),
            brand = brand2,
            categoryName = ProductCategory.BOTTOM
        )

        `when`(productRepository.findCheapestProductsByCategory())
            .thenReturn(Flux.just(product1, product2))

        // when
        val result = productService.getCategoryMinPricesWithTotalAmount()

        // then
        StepVerifier.create(result)
            .expectNextMatches { response ->
                response.categories.size == 2 &&
                response.totalLowestPrice == 30000L &&
                response.categories[0].category == ProductCategory.TOP &&
                response.categories[0].lowestProduct.brand.name == "Brand1" &&
                response.categories[0].lowestProduct.price == 10000L &&
                response.categories[1].category == ProductCategory.BOTTOM &&
                response.categories[1].lowestProduct.brand.name == "Brand2" &&
                response.categories[1].lowestProduct.price == 20000L
            }
            .verifyComplete()
    }
} 