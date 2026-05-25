package com.gblrod.orbvault.ui.countries.presentation.explore.statistics.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.countries.presentation.explore.statistics.components.CountriesHighlights
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.countries.presentation.state.StatsUiState
import com.gblrod.orbvault.ui.shared.components.ErrorMessage
import com.gblrod.orbvault.ui.shared.components.LoadingScreen
import com.gblrod.orbvault.ui.shared.extensions.getErrorMessage

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
            val errorMessage = getErrorMessage(
                showDetailedError = showDetailedError,
                genericErrorResId = R.string.stats_ui_state_generic_error,
                messageResId = state.messageResId,
                code = state.code
            )
            ErrorMessage(
                message = errorMessage,
                onRetry = { exploreViewModel.allRetry() }
            )
        }
    }
}