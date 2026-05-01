package com.gblrod.orbvault.ui.countries.presentation.explore.news.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class NewItem(
    val label: String,
    val value: String,
    val icon: ImageVector,
    val onClick: (() -> Unit),
    val backgroundColor: Color,
    val iconColor: Color
)