package com.gblrod.orbvault.ui.presentation.favorites.screen

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.presentation.favorites.components.FavoriteItems

@Composable
fun FavoritesScreen(
    countryDetailsViewModel: CountryDetailsViewModel,
    snackbarHostState: SnackbarHostState
) {
    val favorites by countryDetailsViewModel.favorites.collectAsState()
    val secondValue = when {
        favorites.isEmpty() -> {
            stringResource(id = R.string.favorite_list_details_empty)
        }

        favorites.size == 1 -> {
            stringResource(id = R.string.favorite_list_details_one, favorites.size)
        }

        else -> {
            stringResource(id = R.string.favorite_list_details, favorites.size)
        }
    }
    FavoriteItems(
        viewModel = countryDetailsViewModel,
        primaryValue = stringResource(id = R.string.favorite_list_label),
        secondValue = secondValue,
        colorCustom = Color.Yellow,
        onClick = { code ->
            if (code != null) {
                countryDetailsViewModel.fetchCountryByCode(code)
            }
        },
        snackbarHostState = snackbarHostState
    )
}