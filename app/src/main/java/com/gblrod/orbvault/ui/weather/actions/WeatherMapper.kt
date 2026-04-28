package com.gblrod.orbvault.ui.weather.actions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material.icons.filled.Umbrella
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector
import com.gblrod.orbvault.R

object WeatherMapper {

    fun getDesc(code: Int): Int {
        return when (code) {
            0 -> R.string.weather_clear_sky
            1, 2, 3 -> R.string.weather_partly_cloudy
            45, 48 -> R.string.weather_fog
            51, 53, 55 -> R.string.weather_drizzle
            61, 63, 65 -> R.string.weather_rain
            71, 73, 75 -> R.string.weather_snow
            95 -> R.string.weather_storm
            else -> R.string.weather_unknown
        }
    }

    fun getIcon(code: Int): ImageVector {
        return when (code) {
            0 -> Icons.Default.WbSunny
            1, 2, 3 -> Icons.Default.Cloud
            61, 63, 65 -> Icons.Default.Umbrella
            else -> Icons.Default.CloudQueue
        }
    }
}