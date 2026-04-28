package com.gblrod.orbvault.ui.shared.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.theme.YellowActions

@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onClick: () -> Unit
) {
    val icon =
        if (isFavorite) Icons.Default.Star
        else Icons.Default.StarBorder

    val description =
        if (isFavorite) stringResource(id = R.string.cd_remove_favorite)
        else stringResource(id = R.string.cd_add_favorite)

    val tint =
        if (isFavorite) YellowActions
        else MaterialTheme.colorScheme.onSurface

    IconButton(
        onClick = { onClick() },
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = tint
        )
    }
}