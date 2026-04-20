package com.gblrod.orbvault.ui.presentation.explore.populated.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.gblrod.orbvault.components.ErrorMessage
import com.gblrod.orbvault.components.LoadingScreen
import com.gblrod.orbvault.ui.presentation.explore.populated.components.PopulatedCountriesList
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.presentation.home.viewmodel.CountriesViewModel
import com.gblrod.orbvault.ui.presentation.state.ExploreUiState

@Composable
fun PopulatedCountriesScreen(
    exploreViewModel: ExploreViewModel,
    countriesViewModel: CountriesViewModel,
    navHostController: NavHostController,
) {
    val uiState by exploreViewModel.exploreUiState.collectAsState()

    when (val state = uiState) {

        is ExploreUiState.Loading -> {
            LoadingScreen()
        }

        is ExploreUiState.Success -> {
            PopulatedCountriesList(
                exploreViewModel = exploreViewModel,
                navHostController = navHostController,
                onCountryClick = { country ->
                    countriesViewModel.fetchCountry(country = country)
                }
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
                onRetry = { exploreViewModel.fetchTopPopulatedCountries() }
            )
        }
    }
}