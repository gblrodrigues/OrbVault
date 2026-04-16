package com.gblrod.orbvault.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
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
import coil.compose.AsyncImage
import com.gblrod.orbvault.components.CountriesUiState
import com.gblrod.orbvault.components.ErrorMessage
import com.gblrod.orbvault.components.SearchBar
import com.gblrod.orbvault.presentation.viewmodel.CountriesViewModel

@Composable
fun OrbVaultScreen(
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
                val country = (uiState as CountriesUiState.Success).country

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Nome: ${country.name.common}"
                    )

                    Text(
                        text = "Nome Of: ${country.name.official}"
                    )

                    Text(
                        text = "Capital: ${country.capital.firstOrNull() ?: "N/A"}"
                    )

                    Text(
                        text = "População: ${country.population}"
                    )

                    Text(
                        text = "Região: ${country.region}"
                    )

                    AsyncImage(
                        model = country.flags.png,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                }
            }

            is CountriesUiState.Error -> {
                val message = if (state.code == null) {
                    stringResource(id = state.messageResId)
                } else {
                    stringResource(id = state.messageResId, state.code)
                }
                ErrorMessage(
                    message = message,
                    country = countryQuery,
                    countriesViewModel = countriesViewModel
                )
            }
        }
    }
}