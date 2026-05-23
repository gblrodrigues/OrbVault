package com.gblrod.orbvault.ui.countries.presentation.explore.continent.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.countries.presentation.explore.all.components.CountriesItems
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.countries.presentation.state.ExploreUiState
import com.gblrod.orbvault.ui.shared.components.ErrorMessage
import com.gblrod.orbvault.ui.shared.components.LoadingScreen
import com.gblrod.orbvault.ui.theme.BlueActions

@Composable
fun ContinentScreen(
    region: String,
    exploreViewModel: ExploreViewModel,
    countryDetailsViewModel: CountryDetailsViewModel
) {
    LaunchedEffect(region) {
        exploreViewModel.fetchCountriesByContinent(region = region)
    }

    val uiState by exploreViewModel.continentCountriesState.collectAsState()

    when (val state = uiState) {
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
                onRetry = { exploreViewModel.allRetry() }
            )
        }

        is ExploreUiState.Success -> {
            CountriesItems(
                countries = state.countries,
                exploreViewModel = exploreViewModel,
                countryDetailsViewModel = countryDetailsViewModel,
                primaryValue = region,
                secondValue = stringResource(
                    id = R.string.continent_all_countries,
                    state.totalCountries
                ),
                colorCustom = BlueActions,
                onClick = { country ->
                    countryDetailsViewModel.fetchCountryByCode(code = country.cca3)
                }
            )
        }

        is ExploreUiState.GlobalStatsSuccess -> {}
    }
}