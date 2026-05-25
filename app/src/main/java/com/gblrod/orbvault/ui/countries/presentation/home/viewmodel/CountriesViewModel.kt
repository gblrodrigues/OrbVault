package com.gblrod.orbvault.ui.countries.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.countries.mapper.isCountryCode
import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto
import com.gblrod.orbvault.data.countries.repository.CountriesRepository
import com.gblrod.orbvault.ui.countries.presentation.state.BordersUiState
import com.gblrod.orbvault.ui.countries.presentation.state.CountriesUiState
import com.gblrod.orbvault.ui.countries.presentation.state.RandomCountryUiState
import com.gblrod.orbvault.ui.shared.utils.safeApiCall
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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

    private val historyStack = mutableListOf<CountriesDto>()
    private var currentCountry: CountriesDto? = null
    private val _previewReturnCountry = MutableStateFlow(false)
    val previewReturnCountry: StateFlow<Boolean> = _previewReturnCountry

    private val _countryQuery = MutableStateFlow("")
    val countryQuery: StateFlow<String> = _countryQuery

    private var lastRequest: (() -> Unit)? = null

    fun fetchCountry(country: String?) {
        if (country.isNullOrBlank()) return

        lastRequest = {
            fetchCountry(country)
        }

        job?.cancel()

        job = viewModelScope.launch {

            _countriesUiState.value = CountriesUiState.Loading
            _bordersUiState.value = BordersUiState.Idle

            safeApiCall(
                onHttpError = { code ->
                    if (code == 404) {
                        _countriesUiState.value =
                            CountriesUiState.Error(messageResId = R.string.countries_ui_state_not_found)

                    } else {
                        _countriesUiState.value = CountriesUiState.Error(
                            messageResId = R.string.ui_state_http_exception,
                            code = code
                        )
                    }
                },
                onIoError = {
                    _countriesUiState.value =
                        CountriesUiState.Error(messageResId = R.string.ui_state_io_exception)
                },
                onGenericError = {
                    _countriesUiState.value =
                        CountriesUiState.Error(messageResId = R.string.ui_state_generic_error)
                }
            ) {
                val result = if (isCountryCode(country)) {
                    repository.fetchCountryByCode(country.uppercase())
                } else {
                    repository.fetchCountry(name = country)
                }

                if (result != null) {
                    _countriesUiState.value =
                        CountriesUiState.Success(country = result, code = result)
                } else {
                    _countriesUiState.value =
                        CountriesUiState.Error(R.string.countries_ui_state_not_found)
                }
            }
        }
    }

    fun fetchRandomCountry() {
        lastRequest = {
            fetchRandomCountry()
        }

        viewModelScope.launch {
            _randomCountryUiState.value = RandomCountryUiState.Loading

            safeApiCall(
                onHttpError = { code ->
                    _randomCountryUiState.value = RandomCountryUiState.Error(
                        messageResId = R.string.ui_state_http_exception,
                        code = code
                    )
                },
                onIoError = {
                    _randomCountryUiState.value =
                        RandomCountryUiState.Error(messageResId = R.string.ui_state_io_exception)
                },
                onGenericError = {
                    _randomCountryUiState.value =
                        RandomCountryUiState.Error(messageResId = R.string.ui_state_generic_error)
                }
            ) {
                val randomCountry = repository.getRandomCountry()

                currentCountry?.let { historyStack.add(it) }
                _previewReturnCountry.value = historyStack.isNotEmpty()
                currentCountry = randomCountry

                _randomCountryUiState.value = RandomCountryUiState.Success(country = randomCountry)
            }
        }
    }

    fun returnRandomCountry() {
        viewModelScope.launch {
            if (historyStack.isNotEmpty()) {
                val previous = historyStack.removeAt(historyStack.lastIndex)
                currentCountry = previous

                _randomCountryUiState.value = RandomCountryUiState.Success(country = previous)
                _previewReturnCountry.value = historyStack.isNotEmpty()
            }
        }
    }

    fun fetchBorders(country: CountriesDto) {
        lastRequest = {
            fetchBorders(country)
        }

        viewModelScope.launch {
            _bordersUiState.value = BordersUiState.Loading

            safeApiCall(
                onHttpError = { code ->
                    _bordersUiState.value = BordersUiState.Error(
                        messageResId = R.string.ui_state_http_exception,
                        code = code
                    )
                },
                onIoError = {
                    _bordersUiState.value =
                        BordersUiState.Error(messageResId = R.string.ui_state_io_exception)
                },
                onGenericError = {
                    _bordersUiState.value =
                        BordersUiState.Error(messageResId = R.string.ui_state_generic_error)
                }
            ) {
                val neighbors = repository.getBorders(country = country)
                _bordersUiState.value = BordersUiState.Success(neighbors = neighbors)
            }
        }
    }

    fun fetchCountryForRandom(code: String) {
        lastRequest = {
            fetchCountryForRandom(code)
        }

        viewModelScope.launch {
            _randomCountryUiState.value = RandomCountryUiState.Loading
            _bordersUiState.value = BordersUiState.Idle

            safeApiCall(
                onHttpError = { code ->
                    _randomCountryUiState.value =
                        RandomCountryUiState.Error(
                            messageResId = R.string.ui_state_http_exception,
                            code = code
                        )
                },
                onIoError = {
                    _randomCountryUiState.value =
                        RandomCountryUiState.Error(messageResId = R.string.ui_state_io_exception)
                },
                onGenericError = {
                    _randomCountryUiState.value =
                        RandomCountryUiState.Error(messageResId = R.string.ui_state_generic_error)
                }
            ) {
                val result = repository.fetchCountryByCode(code)

                if (result != null) {
                    _randomCountryUiState.value =
                        RandomCountryUiState.Success(country = result)
                } else {
                    _randomCountryUiState.value =
                        RandomCountryUiState.Error(R.string.countries_ui_state_not_found)
                }
            }
        }
    }

    fun onCountryQueryChange(query: String) {
        _countryQuery.value = query
    }

    fun clearSearch() {
        _countryQuery.value = ""
        _countriesUiState.value = CountriesUiState.Idle
    }

    fun retryLastRequest() {
        _countriesUiState.value = CountriesUiState.Loading
        _randomCountryUiState.value = RandomCountryUiState.Loading
        _bordersUiState.value = BordersUiState.Idle

        lastRequest?.invoke()
    }

    fun clearCountry() {
        _countriesUiState.value = CountriesUiState.Idle
    }
}