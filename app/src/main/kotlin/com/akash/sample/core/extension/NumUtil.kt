package com.akash.sample.core.extension

import kotlin.math.roundToInt

fun Double.formatToRupee(): Double {
    return (this * 100.0).roundToInt() / 100.0
}