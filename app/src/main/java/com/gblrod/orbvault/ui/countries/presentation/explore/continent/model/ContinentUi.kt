package com.gblrod.orbvault.ui.countries.presentation.explore.continent.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

data class ContinentUi(
    val image: Painter,
    val color: Color,
    val region: String,
    val label: Int
)