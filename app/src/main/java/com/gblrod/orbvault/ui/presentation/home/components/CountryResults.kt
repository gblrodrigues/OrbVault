package com.gblrod.orbvault.ui.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.ui.shared.components.ErrorMessage
import com.gblrod.orbvault.ui.shared.components.LoadingScreen
import com.gblrod.orbvault.data.dto.CountriesDto
import com.gblrod.orbvault.ui.presentation.state.BordersUiState
import com.gblrod.orbvault.ui.presentation.state.CountriesUiState

@Composable
fun CountryResults(
    state: CountriesUiState,
    bordersState: BordersUiState,
    countryQuery: String,
    onRetry: () -> Unit,
    onFetchCountry: (String) -> Unit,
    onFetchBorders: (CountriesDto) -> Unit,
    onQueryChange: (String) -> Unit
) {
    when (state) {
        is CountriesUiState.Idle -> {}

        is CountriesUiState.Loading -> {
            LoadingScreen()
        }

        is CountriesUiState.Success -> {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                CardCountryDetails(
                    country = state.country,
                    bordersState = bordersState,
                    onFetchBorders = onFetchBorders,
                    onCountryClick = onFetchCountry,
                    countryQuery = onQueryChange
                )
            }
        }

        is CountriesUiState.Error -> {
            Column(
                modifier = Modifier.padding(vertical = 32.dp)
            ) {
                val message = if (state.code == null) {
                    stringResource(id = state.messageResId)
                } else {
                    stringResource(id = state.messageResId, state.code)
                }
                ErrorMessage(
                    message = message,
                    onRetry = onRetry
                )
            }
        }
    }
}