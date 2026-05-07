package com.gblrod.orbvault.ui.countries.presentation.explore.largest.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.gblrod.orbvault.ui.countries.presentation.explore.largest.components.LargestCountriesList
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.countries.presentation.state.ExploreUiState
import com.gblrod.orbvault.ui.shared.components.ErrorMessage
import com.gblrod.orbvault.ui.shared.components.LoadingScreen

@Composable
fun LargestCountriesScreen(
    exploreViewModel: ExploreViewModel,
    countryDetailsViewModel: CountryDetailsViewModel
) {

    LaunchedEffect(Unit) {
        exploreViewModel.fetchTopLargestCountries()
    }

    val uiState by exploreViewModel.largestCountriesState.collectAsState()
    val favorites by countryDetailsViewModel.favorites.collectAsState()

    when (val state = uiState) {

        is ExploreUiState.GlobalStatsSucess -> {}

        is ExploreUiState.Loading -> {
            LoadingScreen()
        }

        is ExploreUiState.Success -> {
            LargestCountriesList(
                countryDetailsViewModel = countryDetailsViewModel,
                countries = state.countries,
                favorites = favorites
            )
        }

        is ExploreUiState.Error -> {
            val message = if (state.code == null) {
                stringResource(id = state.messageResId)
            } else {
                stringResource(id = state.messageResId, state.code)
            }

            ErrorMessage(
                message = message,
                onRetry = { exploreViewModel.fetchTopLargestCountries() }
            )
        }
    }
}