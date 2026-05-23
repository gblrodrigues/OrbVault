package com.gblrod.orbvault.ui.countries.presentation.explore.continent.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

data class ContinentItem(
    val image: Painter,
    val color: Color,
    val label: String,
    val value: String,
    val onClick: (() -> Unit)
)