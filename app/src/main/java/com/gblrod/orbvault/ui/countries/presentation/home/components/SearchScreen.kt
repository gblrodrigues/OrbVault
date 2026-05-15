package com.gblrod.orbvault.ui.countries.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.home.viewmodel.CountriesViewModel
import com.gblrod.orbvault.ui.countries.presentation.state.CountriesUiState

@Composable
fun SearchScreen(
    countriesViewModel: CountriesViewModel,
    countryDetailsViewModel: CountryDetailsViewModel,
    onBack: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focus = LocalFocusManager.current

    val bordersState by countryDetailsViewModel.bordersUiState.collectAsState()
    val recentCountries by countryDetailsViewModel.recentCountries.collectAsState()
    val uiState by countriesViewModel.countriesUiState.collectAsState()
    val countryQuery by countriesViewModel.countryQuery.collectAsState()

    LaunchedEffect(uiState) {
        when (uiState) {
            is CountriesUiState.Success,
            is CountriesUiState.Error -> {
                focus.clearFocus(force = true)
                keyboardController?.hide()
            }

            else -> Unit
        }
    }

    Column {
        Spacer(modifier = Modifier.height(8.dp))

        OrbVaultSearchBar(
            countryQuery = countryQuery,
            onCountryQuery = { query ->
                countriesViewModel.onCountryQueryChange(query)

                if (query.isBlank()) {
                    countriesViewModel.clearCountry()
                }
            },
            onSearch = {
                if (countryQuery.isNotBlank()) {
                    countriesViewModel.fetchCountry(country = countryQuery)
                }
            },
            onBack = { onBack() },
            onClearSearch = { countriesViewModel.clearSearch() }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            focus.clearFocus(force = true)
                            keyboardController?.hide()
                        }
                    )
                }
        ) {
            item {
                ExpandedContent(
                    recentCountries = recentCountries,
                    onCountryClick = { code ->
                        countryDetailsViewModel.onCountrySelected(code)
                    }
                )
            }

            item {
                CountryResults(
                    state = uiState,
                    countryQuery = countryQuery,
                    onQueryChange = { query ->
                        countriesViewModel.onCountryQueryChange(query)
                    },
                    bordersState = bordersState,
                    onRetry = {
                        countriesViewModel.retryLastRequest()
                    },
                    onFetchBorders = { borders ->
                        countryDetailsViewModel.fetchBorders(
                            country = borders
                        )
                    },
                    onFetchCountry = { country ->
                        countriesViewModel.fetchCountry(
                            country = country
                        )
                    },
                    countryDetailsViewModel = countryDetailsViewModel
                )
            }
        }
    }
}