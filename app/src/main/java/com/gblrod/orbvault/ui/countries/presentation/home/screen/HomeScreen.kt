package com.gblrod.orbvault.ui.countries.presentation.home.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.shared.components.ScreenHeader
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.home.components.CountryResults
import com.gblrod.orbvault.ui.countries.presentation.home.components.SearchBar
import com.gblrod.orbvault.ui.countries.presentation.home.viewmodel.CountriesViewModel
import com.gblrod.orbvault.ui.theme.HomeScreenSubTitle

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    countriesViewModel: CountriesViewModel,
    countryDetailsViewModel: CountryDetailsViewModel
) {
    var countryQuery by remember { mutableStateOf("") }
    val uiState by countriesViewModel.countriesUiState.collectAsState()
    val focus = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val bordersState by countryDetailsViewModel.bordersUiState.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focus.clearFocus(force = true)
                })
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            ScreenHeader(
                primaryValue = stringResource(id = R.string.home_screen_title),
                secondValue = stringResource(id = R.string.home_screen_sub_title),
                colorCustom = HomeScreenSubTitle,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            )
        }

        fun searchCountry() {
            if (countryQuery.isNotBlank()) {
                countriesViewModel.fetchCountry(country = countryQuery)
            }
        }

        item {
            SearchBar(
                countryQuery = countryQuery,
                onCountryQuery = {
                    countryQuery = it

                    if (it.isBlank()) {
                        countriesViewModel.clearCountry()
                    }
                },
                onSearch = {
                    searchCountry()
                    focus.clearFocus(force = true)
                    keyboardController?.hide()
                }
            )
        }

        item {
            CountryResults(
                state = uiState,
                countryQuery = countryQuery,
                onQueryChange = { newQuery -> countryQuery = newQuery },
                bordersState = bordersState,
                onRetry = { countriesViewModel.fetchCountry(countryQuery) },
                onFetchBorders = { borders ->
                    countryDetailsViewModel.fetchBorders(country = borders)
                },
                onFetchCountry = { country ->
                    countriesViewModel.fetchCountry(country = country)
                },
                countryDetailsViewModel = countryDetailsViewModel
            )
        }
    }
}