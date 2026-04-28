package com.gblrod.orbvault.ui.weather.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.data.dto.countries.CountriesDto
import com.gblrod.orbvault.data.dto.weather.CurrentWeatherDto

@Composable
fun WeatherContent(
    country: CountriesDto,
    weather: CurrentWeatherDto,
    isFavorite: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        WeatherHeader(
            country = country,
            isFavorite = isFavorite,
            onClick = {
                onClick()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        WeatherCard(weather = weather)

        Spacer(modifier = Modifier.height(8.dp))

        WeatherInfoRow(weather = weather)
    }
}