package com.gblrod.orbvault.ui.countries.presentation.explore.statistics.model

sealed class StatIcon{
    data class Local(
        val resId: Int
    ) : StatIcon()

    data class Remote(
        val url: String
    ) : StatIcon()
}