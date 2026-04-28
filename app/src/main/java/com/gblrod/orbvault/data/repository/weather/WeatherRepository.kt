package com.gblrod.orbvault.data.repository.weather

import com.gblrod.orbvault.data.dto.weather.CurrentWeatherDto
import com.gblrod.orbvault.data.network.weather.WeatherApi

class WeatherRepository(
    val api: WeatherApi
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