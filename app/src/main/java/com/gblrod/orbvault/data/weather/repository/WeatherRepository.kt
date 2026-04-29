package com.gblrod.orbvault.data.weather.repository

import com.gblrod.orbvault.data.weather.remote.api.WeatherAPI
import com.gblrod.orbvault.data.weather.remote.dto.CurrentWeatherDto

class WeatherRepository(
    val api: WeatherAPI
) {
    suspend fun getCurrentWeather(
        latitude: Double,
        longitude: Double
    ): CurrentWeatherDto {
        return api.getCurrentWeather(
            latitude = latitude,
            longitude = longitude
        ).current
    }
}