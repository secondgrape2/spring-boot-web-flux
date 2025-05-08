package com.mycompany.shopping.product.infrastructures.impls

import com.mycompany.shopping.product.infrastructures.ProductEventPublisher
import com.mycompany.shopping.product.event.ProductEvent
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks
import reactor.core.publisher.Flux

@Component
class InMemoryProductEventPublisher : ProductEventPublisher {
    private val sink = Sinks.many().replay().latest<ProductEvent>()

    override fun publish(event: ProductEvent): Mono<Void> {
        return Mono.fromRunnable<Void> {
            sink.tryEmitNext(event)
        }.then()
    }

    fun getEventFlux(): Flux<ProductEvent> = sink.asFlux()
} 