package com.gblrod.orbvault.ui.countries.presentation.explore.comparison.utils

import com.gblrod.orbvault.ui.countries.presentation.explore.comparison.model.ComparisonWinner

fun getWinner(
    primaryValue: Double?,
    secondaryValue: Double?
): ComparisonWinner {
    return when {
        primaryValue == null || secondaryValue == null -> ComparisonWinner.DRAW

        primaryValue > secondaryValue -> ComparisonWinner.PRIMARY

        secondaryValue > primaryValue -> ComparisonWinner.SECONDARY

        else -> ComparisonWinner.DRAW
    }
}