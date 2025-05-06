package com.mycompany.shopping.product.controller

import com.mycompany.shopping.product.service.ProductService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.http.MediaType
import com.mycompany.shopping.common.exception.ErrorResponse
import com.mycompany.shopping.product.exceptions.InvalidCategoryException
import org.junit.jupiter.api.Assertions.assertEquals

@WebFluxTest(ProductController::class)
class ProductControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var productService: ProductService

    @Test
    fun `getCategoryPriceRange should return 400 when invalid category is provided`() {
        val invalidCategory = "invalid_category"

        webTestClient.get()
            .uri("/api/v1/products/categories/$invalidCategory/price-range")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(ErrorResponse::class.java)
            .consumeWith { result ->
                val errorResponse = result.responseBody
                assertEquals("E2100", errorResponse?.code)
                assertEquals(400, errorResponse?.statusCode)
            }

    }
} 