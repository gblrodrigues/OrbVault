package com.gblrod.orbvault.ui.presentation.explore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.dto.CountriesDto
import com.gblrod.orbvault.data.repository.CountriesRepository
import com.gblrod.orbvault.ui.presentation.state.BordersUiState
import com.gblrod.orbvault.ui.presentation.state.CountriesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CountryDetailsViewModel(
    private val repository: CountriesRepository
) : ViewModel() {
    private val _countryDetailsUiState = MutableStateFlow<CountriesUiState>(CountriesUiState.Idle)
    val countryDetailsUiState: StateFlow<CountriesUiState> = _countryDetailsUiState

    private val _bordersUiState = MutableStateFlow<BordersUiState>(BordersUiState.Idle)
    val bordersUiState: StateFlow<BordersUiState> = _bordersUiState

    fun fetchCountryByCode(code: String?) {
        viewModelScope.launch {
            _countryDetailsUiState.value = CountriesUiState.Loading
            _bordersUiState.value = BordersUiState.Idle

            try {
                val result = repository.fetchCountryByCode(code)

                if (result != null) {
                    _countryDetailsUiState.value =
                        CountriesUiState.Success(
                            country = result,
                            code = result)
                } else {
                    _countryDetailsUiState.value =
                        CountriesUiState.Error(messageResId = R.string.countries_ui_state_not_found)
                }

            } catch (e: Exception) {
                _countryDetailsUiState.value =
                    CountriesUiState.Error(messageResId = R.string.countries_ui_state_ioexception)
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
}