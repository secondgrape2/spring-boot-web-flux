package com.mycompany.shopping.product.infrastructures

import com.mycompany.shopping.product.event.ProductEvent
import reactor.core.publisher.Mono

interface ProductEventPublisher {
    fun publish(event: ProductEvent): Mono<Void>
} 