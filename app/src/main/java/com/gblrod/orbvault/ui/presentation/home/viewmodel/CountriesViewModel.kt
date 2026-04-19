package com.gblrod.orbvault.ui.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.dto.CountriesDto
import com.gblrod.orbvault.data.repository.CountriesRepository
import com.gblrod.orbvault.ui.presentation.state.BordersUiState
import com.gblrod.orbvault.ui.presentation.state.CountriesUiState
import com.gblrod.orbvault.ui.presentation.state.RandomCountryUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class CountriesViewModel(
    private val repository: CountriesRepository
) : ViewModel() {
    private var job: Job? = null
    private val _countriesUiState = MutableStateFlow<CountriesUiState>(CountriesUiState.Idle)
    val countriesUiState: StateFlow<CountriesUiState> = _countriesUiState
    private val _randomCountryUiState =
        MutableStateFlow<RandomCountryUiState>(RandomCountryUiState.Idle)
    val randomCountryUiState: StateFlow<RandomCountryUiState> = _randomCountryUiState
    private val _bordersUiState = MutableStateFlow<BordersUiState>(BordersUiState.Idle)
    val bordersUiState: StateFlow<BordersUiState> = _bordersUiState

    fun fetchCountry(country: String?) {
        if (country.isNullOrBlank()) return

        job?.cancel()

        job = viewModelScope.launch {

            _countriesUiState.value = CountriesUiState.Loading
            _bordersUiState.value = BordersUiState.Idle

            try {
                val result = repository.fetchCountry(name = country)

                if (result != null) {
                    _countriesUiState.value =
                        CountriesUiState.Success(country = result, code = result)
                } else {
                    _countriesUiState.value =
                        CountriesUiState.Error(R.string.countries_ui_state_not_found)
                }

            } catch (e: IOException) {
                _countriesUiState.value =
                    CountriesUiState.Error(messageResId = R.string.countries_ui_state_ioexception)

            } catch (e: HttpException) {
                if (e.code() == 404) {
                    _countriesUiState.value =
                        CountriesUiState.Error(messageResId = R.string.countries_ui_state_not_found)

                } else {
                    _countriesUiState.value = CountriesUiState.Error(
                        messageResId = R.string.countries_ui_state_httpexception,
                        code = e.code()
                    )
                }

            } catch (e: Exception) {
                if (e is CancellationException) throw e
                _countriesUiState.value =
                    CountriesUiState.Error(messageResId = R.string.countries_ui_state_cancellationexception)
            }
        }
    }

    fun fetchRandomCountry() {
        viewModelScope.launch {
            _randomCountryUiState.value = RandomCountryUiState.Loading

            try {
                val randomCountry = repository.getRandomCountry()
                _randomCountryUiState.value = RandomCountryUiState.Success(country = randomCountry)

            } catch (e: HttpException) {
                _randomCountryUiState.value = RandomCountryUiState.Error(
                    messageResId = R.string.explore_ui_state_httpexception,
                    code = e.code()
                )

            } catch (e: IOException) {
                _randomCountryUiState.value =
                    RandomCountryUiState.Error(messageResId = R.string.explore_ui_state_ioexception)
            }
        }
    }

    fun fetchBorders(country: CountriesDto) {
        viewModelScope.launch {
            _bordersUiState.value = BordersUiState.Loading

            try {
                val neighbors = repository.getBorders(country = country)
                _bordersUiState.value = BordersUiState.Success(neighbors = neighbors)

            } catch (e: Exception) {
                _bordersUiState.value =
                    BordersUiState.Error(messageResId = R.string.countries_ui_state_httpexception)
            }
        }
    }

    fun fetchCountryForRandom(code: String) {
        viewModelScope.launch {
            _randomCountryUiState.value = RandomCountryUiState.Loading
            _bordersUiState.value = BordersUiState.Idle

            try {
                val result = repository.fetchCountryByCode(code)

                if (result != null) {
                    _randomCountryUiState.value =
                        RandomCountryUiState.Success(country = result)
                } else {
                    _randomCountryUiState.value =
                        RandomCountryUiState.Error(R.string.countries_ui_state_not_found)
                }

            } catch (e: Exception) {
                _randomCountryUiState.value =
                    RandomCountryUiState.Error(messageResId = R.string.explore_ui_state_ioexception)
            } catch (e: Exception) {
                _randomCountryUiState.value =
                    RandomCountryUiState.Error(messageResId = R.string.countries_ui_state_httpexception)
            }
        }
    }

    fun clearCountry() {
        _countriesUiState.value = CountriesUiState.Idle
    }
}