package com.gblrod.orbvault.ui.presentation.home.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.components.ErrorMessage
import com.gblrod.orbvault.components.LoadingScreen
import com.gblrod.orbvault.ui.presentation.home.components.CardCountryDetails
import com.gblrod.orbvault.ui.presentation.home.components.SearchBar
import com.gblrod.orbvault.ui.presentation.home.viewmodel.CountriesViewModel
import com.gblrod.orbvault.ui.presentation.state.CountriesUiState
import com.gblrod.orbvault.ui.theme.HomeScreenSubTitleColor

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    countriesViewModel: CountriesViewModel
) {
    var countryQuery by remember { mutableStateOf("") }
    val uiState by countriesViewModel.countriesUiState.collectAsState()
    val focus = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val bordersState by countriesViewModel.bordersUiState.collectAsState()

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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.home_screen_title),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stringResource(id = R.string.home_screen_sub_title),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = HomeScreenSubTitleColor
                )
            }
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


        when (val state = uiState) {
            is CountriesUiState.Idle -> {}

            is CountriesUiState.Loading -> {
                item {
                    LoadingScreen()
                }
            }

            is CountriesUiState.Success -> {
                item {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        CardCountryDetails(
                            country = state.country,
                            bordersState = bordersState,
                            onFetchBorders = {
                                countriesViewModel.fetchBorders(country = it)
                            },
                            onCountryClick = { country ->
                                countriesViewModel.fetchCountry(country = country)
                            },
                            countryQuery = { newQuery ->
                                countryQuery = newQuery
                            }
                        )
                    }
                }
            }

            is CountriesUiState.Error -> {
                item {
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
                            onRetry = { countriesViewModel.fetchCountry(country = countryQuery) }
                        )
                    }
                }
            }
        }
    }
}