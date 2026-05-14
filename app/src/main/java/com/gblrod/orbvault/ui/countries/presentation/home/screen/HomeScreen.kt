package com.gblrod.orbvault.ui.countries.presentation.home.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.home.components.CountryResults
import com.gblrod.orbvault.ui.countries.presentation.home.components.ExpandedContent
import com.gblrod.orbvault.ui.countries.presentation.home.components.SearchBar
import com.gblrod.orbvault.ui.countries.presentation.home.viewmodel.CountriesViewModel
import com.gblrod.orbvault.ui.shared.components.ScreenHeader
import com.gblrod.orbvault.ui.theme.HomeScreenSubTitle

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    countriesViewModel: CountriesViewModel,
    countryDetailsViewModel: CountryDetailsViewModel
) {
    val uiState by countriesViewModel.countriesUiState.collectAsState()
    val bordersState by countryDetailsViewModel.bordersUiState.collectAsState()
    val recentCountries by countryDetailsViewModel.recentCountries.collectAsState()

    val focus = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var countryQuery by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    BackHandler(enabled = expanded) {
        expanded = false
        focus.clearFocus(force = true)
        keyboardController?.hide()
    }

    fun searchCountry() {
        if (countryQuery.isNotBlank()) {
            countriesViewModel.fetchCountry(country = countryQuery)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .pointerInput(expanded) {
                    if (!expanded) {
                        detectTapGestures {
                            focus.clearFocus(force = true)
                        }
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                AnimatedVisibility(
                    visible = !expanded,
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column {
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

                        expanded = false

                        focus.clearFocus(force = true)
                        keyboardController?.hide()
                    },
                    onExpandedChange = { expanded = it },
                    onClose = {
                        expanded = false
                        focus.clearFocus(force = true)
                        keyboardController?.hide()
                    },
                    expanded = expanded
                )
            }

            item {
                AnimatedVisibility(
                    visible = !expanded,
                    exit = fadeOut()
                ) {
                    CountryResults(
                        state = uiState,
                        countryQuery = countryQuery,
                        onQueryChange = { newQuery -> countryQuery = newQuery },
                        bordersState = bordersState,
                        onRetry = { countriesViewModel.retryLastRequest() },
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

        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Surface(
                modifier = Modifier.fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                focus.clearFocus(force = true)
                                keyboardController?.hide()
                            }
                        )
                    },
                color = MaterialTheme.colorScheme.surface
            ) {
                Column {
                    SearchBar(
                        countryQuery = countryQuery,
                        onCountryQuery = { countryQuery = it },
                        onSearch = {
                            searchCountry()

                            expanded = false

                            focus.clearFocus(force = true)
                            keyboardController?.hide()
                        },
                        onExpandedChange = {},
                        expanded = true,
                        shouldRequestFocus = true,
                        onClose = {
                            expanded = false
                            focus.clearFocus(force = true)
                            keyboardController?.hide()
                        }
                    )
                    ExpandedContent(
                        recentCountries = recentCountries,
                        onCountryClick = { code ->
                            expanded = false

                            focus.clearFocus(force = true)
                            keyboardController?.hide()

                            countryDetailsViewModel.onCountrySelected(code)
                        }
                    )
                }
            }
        }
    }
}