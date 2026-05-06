package com.gblrod.orbvault.ui.weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.weather.repository.WeatherRepository
import com.gblrod.orbvault.ui.weather.state.WeatherUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

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

            try {
                val weather = repository.getCurrentWeather(
                    latitude = latitude,
                    longitude = longitude
                )

                _weatherUiState.value = WeatherUiState.Success(weather = weather)

            } catch (e: HttpException) {
                _weatherUiState.value =
                    WeatherUiState.Error(
                        message = R.string.ui_state_http_exception,
                        code = e.code()
                    )

            } catch (e: IOException) {
                _weatherUiState.value =
                    WeatherUiState.Error(message = R.string.weather_load_error)

            } catch (e: Exception) {
                if (e is CancellationException) throw e
                _weatherUiState.value =
                    WeatherUiState.Error(message = R.string.ui_state_generic_error)
            }
        }
    }

    fun clearState() {
        _weatherUiState.value = WeatherUiState.Idle
    }
}