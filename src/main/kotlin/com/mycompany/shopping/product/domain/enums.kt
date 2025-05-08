package com.mycompany.shopping.product.domain.enums

enum class ProductCategory {
    TOP,
    OUTER,
    BOTTOM,
    SHOES,
    BAG,
    HAT,
    SOCKS,
    ACCESSORY;

    fun getLocalizedName(locale: String = "ko"): String {
        return when (locale) {
            "ko" -> when (this) {
                TOP -> "상의"
                OUTER -> "아우터"
                BOTTOM -> "하의"
                SHOES -> "신발"
                BAG -> "가방"
                HAT -> "모자"
                SOCKS -> "양말"
                ACCESSORY -> "액세서리"
            }
            "en" -> name
            else -> name
        }
    }
} 