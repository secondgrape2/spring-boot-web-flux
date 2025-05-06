package com.mycompany.shopping.product.controller

import com.mycompany.shopping.product.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import com.mycompany.shopping.product.dto.CategoryMinPriceResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import com.mycompany.shopping.common.exception.ErrorResponse
import com.mycompany.shopping.product.dto.BrandLowestPriceResponse
import com.mycompany.shopping.product.dto.CategoryPriceRangeResponse
import com.mycompany.shopping.product.domain.enums.Category
import io.swagger.v3.oas.annotations.Parameter
import com.mycompany.shopping.product.exceptions.InvalidCategoryException

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Product", description = "Product management APIs")
class ProductController(private val productService: ProductService) {

    @Operation(
        summary = "Get minimum prices by category",
        description = "Returns the minimum price for each category and the total amount"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved category minimum prices",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CategoryMinPriceResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )]
            )
        ]
    )
    @GetMapping("/categories/minimum-prices")
    fun getCategoryMinPrices(): Mono<ResponseEntity<CategoryMinPriceResponse>> {
        val minPricesResponse = productService.getCategoryMinPricesWithTotalAmount()
        return minPricesResponse
            .map { response ->
                ResponseEntity.ok(response)
            }
    }

    @Operation(
        summary = "Get brand with lowest total price",
        description = "Returns the brand that offers the lowest total price across all categories"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved brand with lowest total price",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = BrandLowestPriceResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )]
            )
        ]
    )
    @GetMapping("/brands/lowest-total-price")
    fun getBrandWithLowestTotalPrice(): Mono<ResponseEntity<BrandLowestPriceResponse>> {
        return productService.getBrandWithLowestTotalPrice()
            .map { response ->
                ResponseEntity.ok(response)
            }
    }

    @Operation(
        summary = "Get price range by category",
        description = "Returns the lowest and highest priced brands and their products for a given category"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved category price range",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CategoryPriceRangeResponse::class)
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid category name",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class),
                )]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal Server Error",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ErrorResponse::class)
                )]
            )
        ]
    )
    @GetMapping("/categories/{category}/price-range")
    fun getCategoryPriceRange(
        @Parameter(
            name = "category",
            description = "Category name. Must be one of: top, outer, pants, sneakers, bag, hat, socks, accessory",
            required = true,
            example = "top"
        )
        @PathVariable category: String
    ): Mono<ResponseEntity<CategoryPriceRangeResponse>> {
        val parsedCategory: Category = Category.fromValue(category)
            ?: throw InvalidCategoryException()

        return productService.getCategoryPriceRange(parsedCategory)
            .map { response ->
                ResponseEntity.ok(response)
            }
    }
} 