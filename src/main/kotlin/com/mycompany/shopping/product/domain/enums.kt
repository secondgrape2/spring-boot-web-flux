package com.mycompany.shopping.product.domain.enums

import com.fasterxml.jackson.annotation.JsonValue

enum class Category(@JsonValue val value: String) {
    TOP("top"),
    OUTER("outer"),
    PANTS("pants"),
    SNEAKERS("sneakers"),
    BAG("bag"),
    HAT("hat"),
    SOCKS("socks"),
    ACCESSORY("accessory");

    companion object {
        fun fromValue(value: String): Category? {
            return values().find { it.value == value }
        }
    }
} 