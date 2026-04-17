package com.gblrod.orbvault.ui.presentation.home.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.components.ErrorMessage
import com.gblrod.orbvault.ui.presentation.home.components.CardCountryDetails
import com.gblrod.orbvault.ui.presentation.home.components.SearchBar
import com.gblrod.orbvault.ui.presentation.home.viewmodel.CountriesViewModel
import com.gblrod.orbvault.ui.presentation.state.CountriesUiState

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    countriesViewModel: CountriesViewModel
) {
    var countryQuery by remember { mutableStateOf("") }
    val uiState by countriesViewModel.countriesUiState.collectAsState()
    val focus = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        fun searchCountry() {
            if (countryQuery.isNotBlank()) {
                countriesViewModel.fetchCountry(country = countryQuery)
            }
        }

        SearchBar(
            countryQuery = countryQuery,
            onCountryQuery = {
                countryQuery = it
            },
            onSearch = {
                searchCountry()
                focus.clearFocus(force = true)
                keyboardController?.hide()
            }
        )

        when (val state = uiState) {
            is CountriesUiState.Loading -> {}

            is CountriesUiState.Success -> {
                Column(
                    modifier = Modifier.padding(16.dp),
                    content = {
                        CardCountryDetails(countriesViewModel = countriesViewModel)
                    }
                )
            }

            is CountriesUiState.Error -> {
                val message = if (state.code == null) {
                    stringResource(id = state.messageResId)
                } else {
                    stringResource(id = state.messageResId, state.code)
                }
                ErrorMessage(
                    message = message,
                    onRetry = { countriesViewModel.fetchCountry(country = countryQuery) }
                )
            }
        }
    }
}