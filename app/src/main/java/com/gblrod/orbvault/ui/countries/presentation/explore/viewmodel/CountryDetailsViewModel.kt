package com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.countries.local.room.model.FavoriteCountry
import com.gblrod.orbvault.data.countries.local.room.model.RecentCountry
import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto
import com.gblrod.orbvault.data.countries.repository.CountriesRepository
import com.gblrod.orbvault.data.countries.repository.FavoriteRepository
import com.gblrod.orbvault.data.countries.repository.RecentCountryRepository
import com.gblrod.orbvault.ui.countries.presentation.state.BordersUiState
import com.gblrod.orbvault.ui.countries.presentation.state.CountriesUiState
import com.gblrod.orbvault.ui.shared.utils.safeApiCall
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CountryDetailsViewModel(
    private val countriesRepository: CountriesRepository,
    private val favoriteRepository: FavoriteRepository,
    private val recentCountryRepository: RecentCountryRepository
) : ViewModel() {
    private val _countryDetailsUiState = MutableStateFlow<CountriesUiState>(CountriesUiState.Idle)
    val countryDetailsUiState: StateFlow<CountriesUiState> = _countryDetailsUiState

    private val _bordersUiState = MutableStateFlow<BordersUiState>(BordersUiState.Idle)
    val bordersUiState: StateFlow<BordersUiState> = _bordersUiState

    private val _selectedCountryCode = MutableStateFlow<String?>(null)
    val selectedCountryCode: StateFlow<String?> = _selectedCountryCode

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    val favorites = favoriteRepository.getFavorites().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    val recentCountries = recentCountryRepository.getRecentCountries().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )

    fun fetchCountryByCode(code: String?) {
        viewModelScope.launch {
            _countryDetailsUiState.value =
                CountriesUiState.Loading
            _bordersUiState.value = BordersUiState.Idle

            safeApiCall(
                onHttpError = { code ->
                    _countryDetailsUiState.value = CountriesUiState.Error(
                        messageResId = R.string.ui_state_http_exception,
                        code = code
                    )
                },
                onIoError = {
                    _countryDetailsUiState.value =
                        CountriesUiState.Error(
                            messageResId = R.string.country_load_error)
                },
                onGenericError = {
                    _countryDetailsUiState.value =
                        CountriesUiState.Error(messageResId = R.string.ui_state_generic_error)
                }
            ) {
                val result = countriesRepository.fetchCountryByCode(code)
                if (result != null) {
                    saveRecentCountry(result)

                    _countryDetailsUiState.value =
                        CountriesUiState.Success(country = result, code = result)
                } else {
                    _countryDetailsUiState.value =
                        CountriesUiState.Error(messageResId = R.string.countries_ui_state_not_found)
                }
            }
        }
    }

    fun fetchBorders(country: CountriesDto) {
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
                        BordersUiState.Error(messageResId = R.string.neighbors_error)
                },
                onGenericError = {
                    _bordersUiState.value =
                        BordersUiState.Error(messageResId = R.string.ui_state_generic_error)
                }
            ) {
                val neighbors = countriesRepository.getBorders(country = country)
                _bordersUiState.value = BordersUiState.Success(neighbors = neighbors)
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

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun clearSearch() {
        _searchQuery.value = ""
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

    private suspend fun saveRecentCountry(
        country: CountriesDto
    ) {
        recentCountryRepository.insertCountry(
            RecentCountry(
                code = country.cca3,
                name = country.name.common,
                official = country.name.official,
                capital = country.capital?.firstOrNull(),
                region = country.region,
                flagUrl = country.flags.png
            )
        )
    }

    fun dismissBottomSheet() {
        _selectedCountryCode.value = null

        _countryDetailsUiState.value = CountriesUiState.Idle
    }
}