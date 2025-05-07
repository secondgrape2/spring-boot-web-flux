package com.mycompany.shopping.product.controller

import com.mycompany.shopping.product.dto.CreateProductRequestDto
import com.mycompany.shopping.product.dto.UpdateProductRequestDto
import com.mycompany.shopping.product.dto.ProductResponseDto
import com.mycompany.shopping.product.repository.impl.ProductR2dbcRepository
import com.mycompany.shopping.brand.service.BrandService
import com.mycompany.shopping.category.service.CategoryService
import com.mycompany.shopping.product.service.ProductService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.test.StepVerifier
import com.mycompany.shopping.brand.dto.CreateBrandRequestDto
import com.mycompany.shopping.product.domain.enums.ProductCategory
import com.mycompany.shopping.category.repository.impl.CategoryR2dbcRepository
import com.mycompany.shopping.category.dto.CategoryResponseDto

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class ProductControllerIntegrationTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var productRepository: ProductR2dbcRepository

    @Autowired
    private lateinit var categoryRepository: CategoryR2dbcRepository

    @Autowired
    private lateinit var productService: ProductService

    @Autowired
    private lateinit var brandService: BrandService

    @Autowired
    private lateinit var categoryService: CategoryService

    @AfterEach
    fun cleanup() {
        // Clean up all related data after each test
        productRepository.deleteAll().block()
    }

    @Test
    fun `should create product and verify in database`() {
        // Create brand and category first
        val brand = brandService.createBrand(
            CreateBrandRequestDto(name = "Test Brand")
        ).block()!!

        val category = categoryService.getCategoryByName("TOP").block()!!

        val createRequest = CreateProductRequestDto(
            name = "Test Product",
            price = 10000L,
            brandId = brand.id,
            categoryId = category.id
        )

        val response = webTestClient.post()
            .uri("/api/v1/products")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(createRequest)
            .exchange()
            .expectStatus().isCreated
            .expectBody(ProductResponseDto::class.java)
            .returnResult()
            .responseBody!!

        StepVerifier.create(productRepository.findById(response.id))
            .expectNextMatches { product ->
                product.id == response.id &&
                product.name == "Test Product" &&
                product.price == 10000L &&
                product.brandId == brand.id &&
                product.categoryId == category.id &&
                product.deletedAt == null
            }
            .verifyComplete()
    }

    @Test
    fun `should update product and verify in database`() {
        // Create brand and category first
        val brand = brandService.createBrand(
            CreateBrandRequestDto(name = "Test Brand")
        ).block()!!

        val category = categoryService.getCategoryByName("TOP").block()!!

        // Create a product first using service
        val product = productService.createProduct(
            CreateProductRequestDto(
                name = "Original Product",
                price = 10000L,
                brandId = brand.id,
                categoryId = category.id
            )
        ).block()!!

        val updateRequest = UpdateProductRequestDto(
            name = "Updated Product",
            price = 20000L,
            brandId = brand.id,
            categoryId = category.id
        )

        webTestClient.put()
            .uri("/api/v1/products/${product.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(updateRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody(ProductResponseDto::class.java)
            .returnResult()
            .responseBody!!

        StepVerifier.create(productRepository.findById(product.id))
            .expectNextMatches { updatedProduct ->
                updatedProduct.id == product.id &&
                updatedProduct.name == "Updated Product" &&
                updatedProduct.price == 20000L &&
                updatedProduct.brandId == brand.id &&
                updatedProduct.categoryId == category.id &&
                updatedProduct.deletedAt == null
            }
            .verifyComplete()
    }

    @Test
    fun `should delete product and verify in database`() {
        // Create brand and category first
        val brand = brandService.createBrand(
            CreateBrandRequestDto(name = "Test Brand")
        ).block()!!

        val category = categoryService.getCategoryByName("TOP").block()!!

        // Create a product first using service
        val product = productService.createProduct(
            CreateProductRequestDto(
                name = "Test Product",
                price = 10000L,
                brandId = brand.id,
                categoryId = category.id
            )
        ).block()!!

        webTestClient.delete()
            .uri("/api/v1/products/${product.id}")
            .exchange()
            .expectStatus().isNoContent

        StepVerifier.create(productRepository.findById(product.id))
            .expectNextMatches { deletedProduct ->
                deletedProduct.deletedAt != null
            }
            .verifyComplete()
    }
} 