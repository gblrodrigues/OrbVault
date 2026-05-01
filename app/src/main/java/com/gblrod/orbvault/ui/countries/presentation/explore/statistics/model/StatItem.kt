package com.gblrod.orbvault.ui.countries.presentation.explore.statistics.model

data class StatItem(
    val label: String,
    val value: String,
    val icon: StatIcon,
    val onClick: (() -> Unit)? = null
)