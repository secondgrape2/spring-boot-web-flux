package com.mycompany.shopping.product.domain.enums

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class ProductCategoryTest {

    @Test
    fun `getLocalizedName returns Korean name when locale is ko`() {
        assertEquals("상의", ProductCategory.TOP.getLocalizedName("ko"))
        assertEquals("아우터", ProductCategory.OUTER.getLocalizedName("ko"))
        assertEquals("하의", ProductCategory.BOTTOM.getLocalizedName("ko"))
        assertEquals("신발", ProductCategory.SHOES.getLocalizedName("ko"))
        assertEquals("가방", ProductCategory.BAG.getLocalizedName("ko"))
        assertEquals("모자", ProductCategory.HAT.getLocalizedName("ko"))
        assertEquals("양말", ProductCategory.SOCKS.getLocalizedName("ko"))
        assertEquals("액세서리", ProductCategory.ACCESSORY.getLocalizedName("ko"))
    }

    @Test
    fun `getLocalizedName returns English name when locale is en`() {
        assertEquals("TOP", ProductCategory.TOP.getLocalizedName("en"))
        assertEquals("OUTER", ProductCategory.OUTER.getLocalizedName("en"))
        assertEquals("BOTTOM", ProductCategory.BOTTOM.getLocalizedName("en"))
        assertEquals("SHOES", ProductCategory.SHOES.getLocalizedName("en"))
        assertEquals("BAG", ProductCategory.BAG.getLocalizedName("en"))
        assertEquals("HAT", ProductCategory.HAT.getLocalizedName("en"))
        assertEquals("SOCKS", ProductCategory.SOCKS.getLocalizedName("en"))
        assertEquals("ACCESSORY", ProductCategory.ACCESSORY.getLocalizedName("en"))
    }

    @Test
    fun `getLocalizedName returns English name when locale is not supported`() {
        assertEquals("TOP", ProductCategory.TOP.getLocalizedName("fr"))
        assertEquals("OUTER", ProductCategory.OUTER.getLocalizedName("jp"))
    }

    @Test
    fun `getLocalizedName returns Korean name when locale is not specified`() {
        assertEquals("상의", ProductCategory.TOP.getLocalizedName())
        assertEquals("아우터", ProductCategory.OUTER.getLocalizedName())
    }
} 