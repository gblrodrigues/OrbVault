package com.gblrod.orbvault.ui.countries.presentation.explore.statistics.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.gblrod.orbvault.ui.countries.presentation.explore.statistics.components.CountriesHighlights
import com.gblrod.orbvault.ui.countries.presentation.explore.statistics.extensions.getErrorMessage
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.countries.presentation.state.StatsUiState
import com.gblrod.orbvault.ui.shared.components.ErrorMessage
import com.gblrod.orbvault.ui.shared.components.LoadingScreen

@Composable
fun StatisticsScreen(
    exploreViewModel: ExploreViewModel,
    countryDetailsViewModel: CountryDetailsViewModel,
    showDetailedError: Boolean = true
) {
    val uiState by exploreViewModel.statsState.collectAsState()
    when (val state = uiState) {
        is StatsUiState.Success -> {
            CountriesHighlights(
                exploreViewModel = exploreViewModel,
                onCountryClick = { code ->
                    countryDetailsViewModel.onCountrySelected(code)
                }
            )
        }

        is StatsUiState.Loading -> {
            LoadingScreen()
        }

        is StatsUiState.Error -> {

            ErrorMessage(
                message = state.getErrorMessage(showDetailedError = showDetailedError),
                onRetry = { exploreViewModel.fetchCountriesStats() }
            )
        }
    }
}