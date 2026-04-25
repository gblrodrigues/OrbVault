package com.gblrod.orbvault.ui.presentation.explore.random.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.ui.presentation.explore.random.components.RandomCountryDetails
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.presentation.home.viewmodel.CountriesViewModel
import com.gblrod.orbvault.ui.presentation.state.RandomCountryUiState
import com.gblrod.orbvault.ui.shared.components.ErrorMessage
import com.gblrod.orbvault.ui.shared.components.LoadingScreen

@Composable
fun RandomCountryScreen(
    countriesViewModel: CountriesViewModel,
    countryDetailsViewModel: CountryDetailsViewModel
) {
    val uiState by countriesViewModel.randomCountryUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (val state = uiState) {
            is RandomCountryUiState.Idle -> {}

            is RandomCountryUiState.Loading -> {
                LoadingScreen()
            }

            is RandomCountryUiState.Success -> {
                RandomCountryDetails(
                    countriesViewModel = countriesViewModel,
                    country = state.country,
                    onCountryClick = { code ->
                        countriesViewModel.fetchCountryForRandom(code = code)
                    },
                    countryQuery = {},
                    countryDetailsViewModel = countryDetailsViewModel
                )
            }

            is RandomCountryUiState.Error -> {
                val message = if (state.code == null) {
                    stringResource(id = state.messageResId)
                } else {
                    stringResource(id = state.messageResId, state.code)
                }

                ErrorMessage(
                    message = message,
                    onRetry = { countriesViewModel.fetchRandomCountry() }
                )
            }
        }
    }
}