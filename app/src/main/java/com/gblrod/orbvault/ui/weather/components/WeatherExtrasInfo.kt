package com.gblrod.orbvault.ui.weather.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.weather.remote.dto.CurrentWeatherDto

@Composable
fun WeatherExtrasInfo(
    weatherDto: CurrentWeatherDto
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        InfoItem(
            modifier = Modifier.weight(1f),
            label = stringResource(id = R.string.weather_label_feels_like),
            value = stringResource(
                id = R.string.weather_temperature,
                weatherDto.apparentTemperature
            )
        )

        InfoItem(
            modifier = Modifier.weight(1f),
            label = stringResource(id = R.string.weather_label_humidity),
            value = stringResource(id = R.string.weather_humidity, weatherDto.humidity)
        )
    }
}