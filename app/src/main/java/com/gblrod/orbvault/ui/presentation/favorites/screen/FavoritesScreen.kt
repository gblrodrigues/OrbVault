package com.gblrod.orbvault.ui.presentation.favorites.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.gblrod.orbvault.R

@Composable
fun FavoritesScreen(modifier: Modifier = Modifier) {
    val newFeature = stringResource(id = R.string.new_feature_coming_soon)

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = newFeature.uppercase(),
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}