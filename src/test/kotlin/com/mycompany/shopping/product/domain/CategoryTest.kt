package com.mycompany.shopping.product.domain.enums

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class CategoryTest {

    @Test
    fun `fromValue should return correct Category for valid value`() {
        val validValues = mapOf(
            "top" to ProductCategory.TOP,
            "outer" to ProductCategory.OUTER,
            "pants" to ProductCategory.PANTS,
            "sneakers" to ProductCategory.SNEAKERS,
            "bag" to ProductCategory.BAG,
            "hat" to ProductCategory.HAT,
            "socks" to ProductCategory.SOCKS,
            "accessory" to ProductCategory.ACCESSORY
        )

        validValues.forEach { (value, expectedCategory) ->
            val result = ProductCategory.fromValue(value)
            assertEquals(expectedCategory, result, "Should return $expectedCategory for value '$value'")
        }
    }

    @Test
    fun `fromValue should return null for invalid value`() {
        val invalidValues = listOf(
            "invalid",
            "tops",
            "OUTER",
            "Pants",
            "sneaker",
            "bags",
            "hats",
            "sock",
            "accessories",
            "",
            " "
        )

        invalidValues.forEach { value ->
            val result = ProductCategory.fromValue(value)
            assertNull(result, "Should return null for invalid value '$value'")
        }
    }

    @Test
    fun `fromValue should be case sensitive`() {
        val caseSensitiveValues = listOf(
            "TOP",
            "Outer",
            "PANTS",
            "Sneakers",
            "BAG",
            "Hat",
            "SOCKS",
            "Accessory"
        )

        caseSensitiveValues.forEach { value ->
            val result = ProductCategory.fromValue(value)
            assertNull(result, "Should return null for case-sensitive value '$value'")
        }
    }
        
} 