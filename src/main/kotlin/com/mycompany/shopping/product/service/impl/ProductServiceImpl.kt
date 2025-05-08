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
import com.mycompany.shopping.product.infrastructures.ProductEventPublisher
import com.mycompany.shopping.product.event.ProductEvent
import org.slf4j.LoggerFactory
import com.mycompany.shopping.product.domain.MinMaxPriceProductWithBrandDomain

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val productMapper: ProductMapper,
    private val eventPublisher: ProductEventPublisher,
    private val brandService: BrandService,
    private val categoryService: CategoryService
) : ProductService {
    private val logger = LoggerFactory.getLogger(ProductServiceImpl::class.java)

    override fun findCheapestProductsByCategory(): Flux<ProductWithBrand> {
        return productRepository.findCheapestProductsByCategory()
    }

    override fun createProduct(request: CreateProductRequestDto): Mono<ProductResponseDto> {
        return Mono.zip(
            brandService.getBrandById(request.brandId),
            categoryService.getCategoryById(request.categoryId)
        )
        .flatMap { tuple -> 
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
            productRepository.create(productDomain)
        }
        .flatMap { savedProduct ->
            eventPublisher.publish(ProductEvent(product = savedProduct, eventType = ProductEvent.EventType.CREATED))
                .thenReturn(savedProduct)
        }
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
            .flatMap { updatedProduct ->
                eventPublisher.publish(ProductEvent(product = updatedProduct, eventType = ProductEvent.EventType.UPDATED))
                    .thenReturn(updatedProduct)
            }
            .map { product -> productMapper.toResponseDto(product) }
    }

    override fun deleteProduct(id: Long): Mono<Void> {
        return productRepository.findById(id)
            .switchIfEmpty(Mono.error(ProductNotFoundException()))
            .flatMap { product ->
                productRepository.softDelete(id)
                    .then(Mono.just(product))
            }
            .doOnSuccess { product ->
                eventPublisher.publish(ProductEvent(product = product, eventType = ProductEvent.EventType.DELETED))
            }
            .then()
    }

    override fun findMinMaxPriceProductsWithBrandByCategoryId(categoryId: Long): Mono<MinMaxPriceProductWithBrandDomain> {
        return productRepository.findMinMaxPriceProductsWithBrandByCategoryId(categoryId)
    }

    override fun calculateMinPriceSumByCategoryForBrand(brandId: Long): Mono<Long> {
        return productRepository.calculateMinPriceSumByCategoryForBrand(brandId)
    }
} 