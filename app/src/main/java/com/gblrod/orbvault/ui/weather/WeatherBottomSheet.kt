package com.gblrod.orbvault.ui.weather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.weather.components.WeatherContent
import com.gblrod.orbvault.ui.weather.state.WeatherUiState
import com.gblrod.orbvault.ui.weather.viewmodel.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherBottomSheet(
    country: CountriesDto,
    weatherViewModel: WeatherViewModel,
    onDismiss: () -> Unit,
    countryDetailsViewModel: CountryDetailsViewModel
) {
    val uiState by weatherViewModel.weatherUiState.collectAsState()
    val favorites by countryDetailsViewModel.favorites.collectAsState()
    val isFavorite = favorites.any { it.code == country.cca3 }

    ModalBottomSheet(
        onDismissRequest = {
            weatherViewModel.clearState()
            onDismiss()
        }
    ) {
        when (val state = uiState) {
            is WeatherUiState.Idle -> {}

            is WeatherUiState.Loading -> {}

            is WeatherUiState.Success -> {
                WeatherContent(
                    country = country,
                    weather = state.weather,
                    isFavorite = isFavorite,
                    onClick = {
                        countryDetailsViewModel.toggleFavorite(country)
                    }
                )
            }

            is WeatherUiState.Error -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = state.message),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}