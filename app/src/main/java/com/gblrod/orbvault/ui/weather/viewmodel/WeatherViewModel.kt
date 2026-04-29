package com.gblrod.orbvault.ui.weather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.weather.repository.WeatherRepository
import com.gblrod.orbvault.ui.weather.state.WeatherUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import kotlin.coroutines.cancellation.CancellationException

class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {
    private val _weatherUiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Idle)
    val weatherUiState = _weatherUiState.asStateFlow()

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

                _weatherUiState.value =
                    WeatherUiState.Success(weather = weather)

            } catch (e: IOException) {
                _weatherUiState.value =
                    WeatherUiState.Error(
                        message = R.string.weather_ui_state_ioexception
                    )
            } catch (e: HttpException) {
                _weatherUiState.value =
                    WeatherUiState.Error(
                        message = R.string.weather_ui_state_httpexception,
                        code = e.code()
                    )
            } catch (e: Exception) {
                if (e is CancellationException) throw e

                _weatherUiState.value =
                    WeatherUiState.Error(
                        message = R.string.weather_ui_state_generic_error
                    )
            }
        }
    }

    fun clearState() {
        _weatherUiState.value = WeatherUiState.Idle
    }
}