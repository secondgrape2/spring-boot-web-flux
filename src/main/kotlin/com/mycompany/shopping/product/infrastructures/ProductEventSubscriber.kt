package com.mycompany.shopping.product.infrastructures

import com.mycompany.shopping.product.event.ProductEvent
import reactor.core.publisher.Flux

interface ProductEventSubscriber {
    fun <T : ProductEvent> subscribe(eventType: Class<T>): Flux<T>
} 