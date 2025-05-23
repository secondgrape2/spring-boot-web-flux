package com.mycompany.shopping.product.controller

import com.mycompany.shopping.product.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import com.mycompany.shopping.common.exception.ErrorResponse
import com.mycompany.shopping.product.dto.BrandLowestPriceResponseDto
import com.mycompany.shopping.product.dto.CategoryPriceRangeResponseDto
import com.mycompany.shopping.product.domain.enums.ProductCategory
import io.swagger.v3.oas.annotations.Parameter
import com.mycompany.shopping.product.exceptions.InvalidCategoryException
import com.mycompany.shopping.product.dto.*
import com.mycompany.shopping.product.service.ShoppingProductService
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Product", description = "Product management APIs")
class ShoppingProductController(
    private val shoppingProductService: ShoppingProductService,
    private val productService: ProductService
) {

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
                    schema = Schema(implementation = CategoryMinPriceResponseDto::class)
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
    @ResponseStatus(HttpStatus.OK)
    fun getCategoryMinPrices(): Mono<CategoryMinPriceResponseDto> {
        val minPricesResponse = shoppingProductService.getCategoryMinPricesWithTotalAmount()
        return minPricesResponse
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
                    schema = Schema(implementation = BrandLowestPriceResponseDto::class)
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
    @ResponseStatus(HttpStatus.OK)
    fun getBrandWithLowestTotalPrice(): Mono<BrandLowestPriceResponseDto> {
        return shoppingProductService.getBrandWithLowestTotalPrice()
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
                    schema = Schema(implementation = CategoryPriceRangeResponseDto::class)
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
    @GetMapping("/categories/{categoryId}/price-range")
    @ResponseStatus(HttpStatus.OK)
    fun getCategoryPriceRange(
        @Parameter(
            name = "categoryId",
            description = "Category ID",
            required = true,
            example = "1"
        )
        @PathVariable categoryId: Long
    ): Mono<CategoryPriceRangeResponseDto> {

        return shoppingProductService.getCategoryPriceRange(categoryId)
    }

    @Operation(
        summary = "Get all products",
        description = "Returns a list of all products with pagination metadata"
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Successfully retrieved all products",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ProductListData::class)
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
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAllProducts(): Mono<ProductListData> {
        // TODO: Add pagination implementation
        return productService.getAllProducts()
            .collectList()
            .map { products ->
                ProductListData(
                    data = products,
                    totalCount = products.size.toLong(),
                    totalPages = 1,
                    currentPage = 0,
                    pageSize = products.size
                )
            }
    }

    @Operation(
        summary = "Create a new product",
        description = "Creates a new product with the provided details"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProduct(@RequestBody dto: CreateProductRequestDto): Mono<ProductResponseDto> {
        return productService.createProduct(dto)
    }

    @Operation(
        summary = "Update a product",
        description = "Updates a product with the provided details"
    )
    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateProduct(
        @PathVariable productId: Long,
        @RequestBody dto: UpdateProductRequestDto
    ): Mono<ProductResponseDto> {
        return productService.updateProduct(productId, dto)
    }

    @Operation(
        summary = "Delete a product",
        description = "Deletes a product with the provided ID"
    )
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProduct(@PathVariable productId: Long): Mono<Void> {
        return productService.deleteProduct(productId)
    }
} 