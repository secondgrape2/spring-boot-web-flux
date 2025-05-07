package com.mycompany.shopping.category.service.impl

import com.mycompany.shopping.category.dto.CategoryResponseDto
import com.mycompany.shopping.category.repository.CategoryRepository
import com.mycompany.shopping.category.service.CategoryService
import com.mycompany.shopping.category.mapper.CategoryMapper
import com.mycompany.shopping.category.exception.CategoryNotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository
) : CategoryService {

    override fun getAllCategories(): Flux<CategoryResponseDto> {
        return categoryRepository.findAll()
            .map { CategoryMapper.toResponseDto(it) }
    }

    override fun getCategoryByName(name: String): Mono<CategoryResponseDto> {
        return categoryRepository.findByName(name)
            .map { CategoryMapper.toResponseDto(it) }
            .switchIfEmpty(Mono.error(CategoryNotFoundException(name)))
    }
}