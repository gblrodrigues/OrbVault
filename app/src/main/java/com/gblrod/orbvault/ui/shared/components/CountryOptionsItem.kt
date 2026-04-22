package com.gblrod.orbvault.ui.shared.components

import androidx.compose.ui.graphics.vector.ImageVector

data class CountryOptionsItem(
    val title: String,
    val subTitle: String,
    val leadingIcon: ImageVector,
    val trailingIcon: ImageVector,
    val onClick: () -> Unit,
)
