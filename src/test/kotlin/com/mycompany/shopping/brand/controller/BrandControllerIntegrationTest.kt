package com.mycompany.shopping.brand.controller

import com.mycompany.shopping.brand.dto.CreateBrandRequestDto
import com.mycompany.shopping.brand.dto.UpdateBrandRequestDto
import com.mycompany.shopping.brand.dto.BrandResponseDto
import com.mycompany.shopping.brand.repository.impl.BrandR2dbcRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.test.StepVerifier
import com.mycompany.shopping.brand.domain.BrandDomain
import com.mycompany.shopping.brand.service.BrandService

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class BrandControllerIntegrationTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var brandRepository: BrandR2dbcRepository

    @Autowired
    private lateinit var brandService: BrandService

    @AfterEach
    fun cleanup() {
        // TODO: How to handle cleanup?
    }

    @Test
    fun `should create brand and verify in database`() {
        val createRequest = CreateBrandRequestDto(name = "Test Brand")

        val response = webTestClient.post()
            .uri("/api/v1/brands")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(createRequest)
            .exchange()
            .expectStatus().isCreated
            .expectBody(BrandResponseDto::class.java)
            .returnResult()
            .responseBody!!

        StepVerifier.create(brandRepository.findById(response.id))
            .expectNextMatches { brand ->
                brand.id == response.id &&
                brand.name == "Test Brand" &&
                brand.deletedAt == null
            }
            .verifyComplete()
    }

    @Test
    fun `should update brand and verify in database`() {
        // Create a brand first using service
        val brand = brandService.createBrand(
            CreateBrandRequestDto(name = "Original Brand")
        ).block()!!

        val updateRequest = UpdateBrandRequestDto(name = "Updated Brand")

        val response = webTestClient.put()
            .uri("/api/v1/brands/${brand.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(updateRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody(BrandResponseDto::class.java)
            .returnResult()
            .responseBody!!

        StepVerifier.create(brandRepository.findById(brand.id))
            .expectNextMatches { updatedBrand ->
                updatedBrand.id == brand.id &&
                updatedBrand.name == "Updated Brand" &&
                updatedBrand.deletedAt == null
            }
            .verifyComplete()
    }

    @Test
    fun `should delete brand and verify in database`() {
        // Create a brand first using service
        val brand = brandService.createBrand(
            CreateBrandRequestDto(name = "Test Brand")
        ).block()!!

        webTestClient.delete()
            .uri("/api/v1/brands/${brand.id}")
            .exchange()
            .expectStatus().isNoContent

        StepVerifier.create(brandRepository.findById(brand.id))
            .expectNextMatches { deletedBrand ->
                deletedBrand.deletedAt != null
            }
            .verifyComplete()
    }
} 