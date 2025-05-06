package com.mycompany.shopping.product.domain.enums

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class CategoryTest {

    @Test
    fun `fromValue should return correct Category for valid value`() {
        val validValues = mapOf(
            "top" to Category.TOP,
            "outer" to Category.OUTER,
            "pants" to Category.PANTS,
            "sneakers" to Category.SNEAKERS,
            "bag" to Category.BAG,
            "hat" to Category.HAT,
            "socks" to Category.SOCKS,
            "accessory" to Category.ACCESSORY
        )

        validValues.forEach { (value, expectedCategory) ->
            val result = Category.fromValue(value)
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
            val result = Category.fromValue(value)
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
            val result = Category.fromValue(value)
            assertNull(result, "Should return null for case-sensitive value '$value'")
        }
    }
        
} 