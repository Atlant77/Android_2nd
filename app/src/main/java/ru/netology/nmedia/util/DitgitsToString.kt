package ru.netology.nmedia.util

import kotlin.math.roundToLong

// Function of digits to string conversions
fun digitsToText(digitsToString: Int): String {
    when (digitsToString) {
        in 0..999 -> return digitsToString.toString().take(3)
        in 1_000..9_999 -> {
            val thousands = (digitsToString / 1_000).toDouble().roundToLong()
            when (val thousandsSecond = (digitsToString % 1_000).toDouble().roundToLong()) {
                0L -> return thousands.toString().take(3) + ".0 K"
                in 1..99 -> return thousands.toString().take(3) + ".0 K"
                in 100..999 -> return thousands.toString().take(3) + ".${
                    thousandsSecond.toString().take(1)
                } K"
                in 1000..9999 -> return "$thousands." + thousandsSecond.toString()
                    .take(1) + "K"
            }
        }
        in 10_000..999_999 -> {
            val thousands = (digitsToString / 1_000).toDouble().roundToLong()
            return thousands.toString().take(3) + "K"
        }
        else -> {
            val millions = (digitsToString / 1_000_000).toDouble().roundToLong()
            val millionsSecond = (digitsToString % 1_000_000).toDouble().roundToLong()
            return if (millions == 0L) {
                millions.toString().take(3) + "M"
            } else "$millions." + millionsSecond.toString()
                .take(1) + "M"
        }
    }
    return digitsToString.toString().take(3)
}