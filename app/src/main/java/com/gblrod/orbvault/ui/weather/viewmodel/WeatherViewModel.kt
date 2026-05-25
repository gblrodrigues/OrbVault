package com.gblrod.orbvault.ui.weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.weather.repository.WeatherRepository
import com.gblrod.orbvault.ui.shared.utils.safeApiCall
import com.gblrod.orbvault.ui.weather.state.WeatherUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {
    private val _weatherUiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val weatherUiState: StateFlow<WeatherUiState> = _weatherUiState

    fun fetchWeather(
        latitude: Double,
        longitude: Double
    ) {
        viewModelScope.launch {
            _weatherUiState.value = WeatherUiState.Loading

            safeApiCall(
                onHttpError = { code ->
                    _weatherUiState.value =
                        WeatherUiState.Error(
                            message = R.string.ui_state_http_exception,
                            code = code
                        )
                },
                onIoError = {
                    _weatherUiState.value =
                        WeatherUiState.Error(message = R.string.weather_load_error)
                },
                onGenericError = {
                    _weatherUiState.value =
                        WeatherUiState.Error(message = R.string.ui_state_generic_error)
                }
            ) {
                val weather = repository.getCurrentWeather(
                    latitude = latitude,
                    longitude = longitude
                )

                _weatherUiState.value = WeatherUiState.Success(weather = weather)
            }
        }
    }

    fun clearState() {
        _weatherUiState.value = WeatherUiState.Idle
    }
}