package com.gblrod.orbvault.ui.presentation.explore.largest.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.gblrod.orbvault.ui.shared.components.ErrorMessage
import com.gblrod.orbvault.ui.shared.components.LoadingScreen
import com.gblrod.orbvault.ui.presentation.explore.largest.components.LargestCountriesList
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.presentation.state.ExploreUiState

@Composable
fun LargestCountriesScreen(
    exploreViewModel: ExploreViewModel,
    countryDetailsViewModel: CountryDetailsViewModel
) {
    val uiState by exploreViewModel.exploreUiState.collectAsState()

    when (val state = uiState) {

        is ExploreUiState.Loading -> {
            LoadingScreen()
        }

        is ExploreUiState.Success -> {
            LargestCountriesList(
                exploreViewModel = exploreViewModel,
                countryDetailsViewModel = countryDetailsViewModel
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