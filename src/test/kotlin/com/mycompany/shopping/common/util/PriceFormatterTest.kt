package com.mycompany.shopping.common.util

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class PriceFormatterTest {

    @Test
    fun `format should add commas for thousands`() {
        // given
        val price = 10000L

        // when
        val formatted = PriceFormatter.format(price)

        // then
        assertEquals("10,000", formatted)
    }

    @Test
    fun `format should handle millions`() {
        // given
        val price = 1000000L

        // when
        val formatted = PriceFormatter.format(price)

        // then
        assertEquals("1,000,000", formatted)
    }

    @Test
    fun `format should handle small numbers`() {
        // given
        val price = 100L

        // when
        val formatted = PriceFormatter.format(price)

        // then
        assertEquals("100", formatted)
    }

    @Test
    fun `format should handle zero`() {
        // given
        val price = 0L

        // when
        val formatted = PriceFormatter.format(price)

        // then
        assertEquals("0", formatted)
    }

    @Test
    fun `format should handle large numbers`() {
        // given
        val price = 999999999L

        // when
        val formatted = PriceFormatter.format(price)

        // then
        assertEquals("999,999,999", formatted)
    }
} 