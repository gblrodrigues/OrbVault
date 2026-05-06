package com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.countries.local.room.model.FavoriteCountry
import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto
import com.gblrod.orbvault.data.countries.repository.CountriesRepository
import com.gblrod.orbvault.data.countries.repository.FavoriteRepository
import com.gblrod.orbvault.ui.countries.presentation.state.BordersUiState
import com.gblrod.orbvault.ui.countries.presentation.state.CountriesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.IOException
import retrofit2.HttpException
import kotlin.coroutines.cancellation.CancellationException

class CountryDetailsViewModel(
    private val countriesRepository: CountriesRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {
    private val _countryDetailsUiState = MutableStateFlow<CountriesUiState>(CountriesUiState.Idle)
    val countryDetailsUiState: StateFlow<CountriesUiState> = _countryDetailsUiState

    private val _bordersUiState = MutableStateFlow<BordersUiState>(BordersUiState.Idle)
    val bordersUiState: StateFlow<BordersUiState> = _bordersUiState

    private val _selectedCountryCode = MutableStateFlow<String?>(null)
    val selectedCountryCode: StateFlow<String?> = _selectedCountryCode

    val favorites = favoriteRepository.getFavorites().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    fun fetchCountryByCode(code: String?) {
        viewModelScope.launch {
            _countryDetailsUiState.value =
                CountriesUiState.Loading
            _bordersUiState.value = BordersUiState.Idle
            try {
                val result = countriesRepository.fetchCountryByCode(code)
                if (result != null) {
                    _countryDetailsUiState.value =
                        CountriesUiState.Success(country = result, code = result)
                } else {
                    _countryDetailsUiState.value =
                        CountriesUiState.Error(messageResId = R.string.countries_ui_state_not_found)
                }
            } catch (e: HttpException) {
                _countryDetailsUiState.value = CountriesUiState.Error(
                    messageResId = R.string.ui_state_http_exception,
                    code = e.code()
                )

            } catch (e: IOException) {
                _countryDetailsUiState.value =
                    CountriesUiState.Error(messageResId = R.string.country_load_error)

            } catch (e: Exception) {
                if (e is CancellationException) throw e
                _countryDetailsUiState.value =
                    CountriesUiState.Error(messageResId = R.string.ui_state_generic_error)
            }
        }
    }

    fun fetchBorders(country: CountriesDto) {
        viewModelScope.launch {
            _bordersUiState.value = BordersUiState.Loading
            try {
                val neighbors = countriesRepository.getBorders(country = country)
                _bordersUiState.value = BordersUiState.Success(neighbors = neighbors)

            } catch (e: HttpException) {
                _bordersUiState.value = BordersUiState.Error(
                    messageResId = R.string.ui_state_http_exception,
                    code = e.code()
                )

            } catch (e: IOException) {
                _bordersUiState.value =
                    BordersUiState.Error(messageResId = R.string.neighbors_error)

            } catch (e: Exception) {
                if (e is CancellationException) throw e
                _bordersUiState.value =
                    BordersUiState.Error(messageResId = R.string.ui_state_generic_error)
            }
        }
    }

    fun toggleFavorite(country: CountriesDto) {
        viewModelScope.launch {
            val isFav = favoriteRepository.isFavorite(code = country.cca3)
            val domain = FavoriteCountry(
                code = country.cca3,
                name = country.name.common,
                official = country.name.official,
                capital = country.capital?.firstOrNull(),
                region = country.region,
                flagUrl = country.flags.png
            )
            val currentList = favorites.value.size

            if (isFav) {
                favoriteRepository.removeFavorite(code = domain.code)
            } else {
                favoriteRepository.addFavorite(country = domain, index = currentList)
            }
        }
    }

    fun removeFavoriteByCode(code: String) {
        viewModelScope.launch {
            favoriteRepository.removeFavorite(code)
        }
    }

    fun restoreFavorite(country: FavoriteCountry, index: Int) {
        viewModelScope.launch {
            favoriteRepository.restoreFavorite(country, index)
        }
    }

    fun onCountrySelected(code: String) {
        if (_selectedCountryCode.value == code) return

        _selectedCountryCode.value = code
        fetchCountryByCode(code)
    }

    fun dismissBottomSheet() {
        _selectedCountryCode.value = null

        _countryDetailsUiState.value = CountriesUiState.Idle
    }
}