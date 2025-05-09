package com.mycompany.shopping.product.event

import com.mycompany.shopping.product.interfaces.Product

data class ProductEvent(
    val eventType: EventType,
    val product: Product
) {
    enum class EventType {
        CREATED,
        UPDATED,
        DELETED,
        DELETED_ALL
    }
} 