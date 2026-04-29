package com.gblrod.orbvault.ui.weather.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Air
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.weather.remote.dto.CurrentWeatherDto

@Composable
fun WeatherInfoRow(
    weather: CurrentWeatherDto
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        InfoCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.Air,
            label = stringResource(id = R.string.weather_label_wind),
            value = stringResource(id = R.string.weather_wind_speed, weather.windSpeed10m)
        )

        InfoCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.AccessTime,
            label = stringResource(id = R.string.weather_label_updated),
            value = weather.time.substringAfter("T")
        )
    }
}