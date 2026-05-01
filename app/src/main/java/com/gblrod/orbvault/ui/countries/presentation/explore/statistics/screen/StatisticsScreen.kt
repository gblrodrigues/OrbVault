package com.gblrod.orbvault.ui.countries.presentation.explore.statistics.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.gblrod.orbvault.ui.countries.presentation.explore.statistics.components.CountriesHighlights
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.countries.presentation.state.StatsUiState
import com.gblrod.orbvault.ui.shared.components.ErrorMessage
import com.gblrod.orbvault.ui.shared.components.LoadingScreen

@Composable
fun StatisticsScreen(
    exploreViewModel: ExploreViewModel,
    countryDetailsViewModel: CountryDetailsViewModel
) {
    val uiState by exploreViewModel.statsState.collectAsState()
    when (val state = uiState) {
        is StatsUiState.Success -> {
            CountriesHighlights(
                exploreViewModel = exploreViewModel,
                countryDetailsViewModel = countryDetailsViewModel
            )
        }

        is StatsUiState.Loading -> {
            LoadingScreen()
        }

        is StatsUiState.Error -> {
            val message = if (state.code == null) {
                stringResource(id = state.messageResId)
            } else {
                stringResource(id = state.messageResId, state.code)
            }

            ErrorMessage(
                message = message,
                onRetry = { exploreViewModel.fetchAllCountries() }
            )
        }
    }
}