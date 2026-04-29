package com.gblrod.orbvault.ui.weather.state

import com.gblrod.orbvault.data.weather.remote.dto.CurrentWeatherDto

sealed class WeatherUiState{
    object Idle : WeatherUiState()

    object Loading : WeatherUiState()

    data class Success(
        val weather: CurrentWeatherDto
    ) : WeatherUiState()

    data class Error(
        val message: Int,
        val code: Int? = null
    ) : WeatherUiState()
}