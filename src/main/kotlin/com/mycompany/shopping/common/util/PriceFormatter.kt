package com.mycompany.shopping.common.util

import java.text.NumberFormat
import java.util.Locale

object PriceFormatter {
    private val numberFormat = NumberFormat.getNumberInstance(Locale.KOREA).apply {
        maximumFractionDigits = 3
        minimumFractionDigits = 0
    }

    fun format(price: Long): String {
        return numberFormat.format(price)
    }
} 