package com.gblrod.orbvault.ui.countries.presentation.explore.statistics.util

import android.icu.text.CompactDecimalFormat
import java.util.Locale

fun formatCompactNumber(value: Double): String {
    val locale = Locale.getDefault()

    val formatter = CompactDecimalFormat.getInstance(
        locale,
        CompactDecimalFormat.CompactStyle.SHORT
    ).apply {
        maximumFractionDigits = 1
    }

    return formatter.format(value)
}