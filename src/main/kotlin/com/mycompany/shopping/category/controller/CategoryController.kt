package com.mycompany.shopping.category.controller

import com.mycompany.shopping.category.dto.CategoryResponseDto
import com.mycompany.shopping.category.service.CategoryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Category", description = "Category management APIs")
class CategoryController(
    private val categoryService: CategoryService
) {
    @GetMapping
    @Operation(summary = "Get all categories", description = "Retrieves a list of all available categories")
    @ResponseStatus(HttpStatus.OK)
    fun getAllCategories(): Flux<CategoryResponseDto> {
        return categoryService.getAllCategories()
    }
} 