package com.gblrod.orbvault.ui.countries.presentation.explore.all.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.gblrod.orbvault.ui.countries.presentation.explore.all.components.AllCountriesList
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.countries.presentation.state.ExploreUiState
import com.gblrod.orbvault.ui.shared.components.ErrorMessage
import com.gblrod.orbvault.ui.shared.components.LoadingScreen

@Composable
fun AllCountriesScreen(
    exploreViewModel: ExploreViewModel,
    countryDetailsViewModel: CountryDetailsViewModel
) {

    LaunchedEffect(Unit) {
        exploreViewModel.fetchAllCountries()
    }

    val uiState by exploreViewModel.allCountriesState.collectAsState()

    when(val state = uiState) {

        is ExploreUiState.Loading -> {
            LoadingScreen()
        }

        is ExploreUiState.Error -> {
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

        is ExploreUiState.Success -> {
            AllCountriesList(
                exploreViewModel = exploreViewModel,
                countryDetailsViewModel = countryDetailsViewModel,
                countriesSize = state.totalCountries
            )
        }

        is ExploreUiState.GlobalStatsSucess -> {}
    }
}