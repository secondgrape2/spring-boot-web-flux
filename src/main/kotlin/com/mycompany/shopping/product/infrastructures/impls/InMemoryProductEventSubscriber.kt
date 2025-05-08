package com.mycompany.shopping.product.infrastructures.impls

import com.mycompany.shopping.product.infrastructures.ProductEventSubscriber
import com.mycompany.shopping.product.event.ProductEvent
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class InMemoryProductEventSubscriber(
    private val eventPublisher: InMemoryProductEventPublisher
) : ProductEventSubscriber {
    override fun <T : ProductEvent> subscribe(eventType: Class<T>): Flux<T> {
        return eventPublisher.getEventFlux()
            .filter { event -> eventType.isInstance(event) }
            .map { event -> eventType.cast(event) }
    }
} 