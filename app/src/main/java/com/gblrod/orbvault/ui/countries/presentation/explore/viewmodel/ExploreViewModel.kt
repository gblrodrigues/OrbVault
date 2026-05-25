package com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.countries.remote.api.CountriesAPI
import com.gblrod.orbvault.ui.countries.presentation.explore.continent.model.ContinentStats
import com.gblrod.orbvault.ui.countries.presentation.explore.continent.state.ContinentUiState
import com.gblrod.orbvault.ui.countries.presentation.explore.statistics.model.AllStats
import com.gblrod.orbvault.ui.countries.presentation.state.ExploreUiState
import com.gblrod.orbvault.ui.countries.presentation.state.StatsUiState
import com.gblrod.orbvault.ui.shared.utils.safeApiCall
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExploreViewModel(
    private val api: CountriesAPI
) : ViewModel() {

    private val _statsState = MutableStateFlow<StatsUiState>(value = StatsUiState.Loading)
    val statsState: StateFlow<StatsUiState> = _statsState

    private val _topPopulatedState =
        MutableStateFlow<ExploreUiState>(value = ExploreUiState.Loading)
    val topPopulatedState: StateFlow<ExploreUiState> = _topPopulatedState

    private val _largestCountriesState =
        MutableStateFlow<ExploreUiState>(value = ExploreUiState.Loading)
    val largestCountriesState: StateFlow<ExploreUiState> = _largestCountriesState

    private val _allCountriesState =
        MutableStateFlow<ExploreUiState>(value = ExploreUiState.Loading)
    val allCountriesState: StateFlow<ExploreUiState> = _allCountriesState

    private val _continentState =
        MutableStateFlow<ContinentUiState>(ContinentUiState.Loading)
    val continentState: StateFlow<ContinentUiState> = _continentState

    private val _continentCountriesState =
        MutableStateFlow<ExploreUiState>(ExploreUiState.Loading)

    val continentCountriesState: StateFlow<ExploreUiState> = _continentCountriesState

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        if (_statsState.value !is StatsUiState.Success) {
            fetchCountriesStats()
        }

        if (_continentState.value !is ContinentUiState.Success) {
            fetchContinentStats()
        }
    }

    fun fetchTopPopulatedCountries() {
        viewModelScope.launch {
            _topPopulatedState.value = ExploreUiState.Loading

            safeApiCall(
                onHttpError = { code ->
                    _topPopulatedState.value = ExploreUiState.Error(
                        messageResId = R.string.ui_state_http_exception,
                        code = code
                    )
                },
                onIoError = {
                    _topPopulatedState.value =
                        ExploreUiState.Error(
                            messageResId = R.string.ui_state_io_exception)
                },
                onGenericError = {
                    _topPopulatedState.value =
                        ExploreUiState.Error(messageResId = R.string.ui_state_generic_error)
                }
            ) {
                val countries = api.getAllCountries()

                val top10PopulatedCountries = countries
                    .sortedByDescending { it.population }
                    .take(n = 10)

                _topPopulatedState.value = ExploreUiState.Success(
                    countries = top10PopulatedCountries,
                    totalCountries = top10PopulatedCountries.size
                )
            }
        }
    }

    fun fetchTopLargestCountries() {
        viewModelScope.launch {
            _largestCountriesState.value = ExploreUiState.Loading

            safeApiCall(
                onHttpError = { code ->
                    _largestCountriesState.value = ExploreUiState.Error(
                        messageResId = R.string.ui_state_http_exception,
                        code = code
                    )
                },
                onIoError = {
                    _largestCountriesState.value =
                        ExploreUiState.Error(
                            messageResId = R.string.ui_state_io_exception)
                },
                onGenericError = {
                    _largestCountriesState.value =
                        ExploreUiState.Error(messageResId = R.string.ui_state_generic_error)
                }
            ) {
                val countries = api.getAllCountries()

                val top10LargestCountries = countries
                    .filter { it.independent == true }
                    .sortedByDescending { it.area }
                    .take(n = 10)

                _largestCountriesState.value =
                    ExploreUiState.Success(
                        countries = top10LargestCountries,
                        totalCountries = top10LargestCountries.size
                    )
            }
        }
    }

    fun fetchAllCountries() {
        viewModelScope.launch {
            _allCountriesState.value = ExploreUiState.Loading

            safeApiCall(
                onHttpError = { code ->
                    _allCountriesState.value = ExploreUiState.Error(
                        messageResId = R.string.ui_state_http_exception,
                        code = code
                    )
                },
                onIoError = {
                    _allCountriesState.value =
                        ExploreUiState.Error(
                            messageResId = R.string.ui_state_io_exception)
                },
                onGenericError = {
                    _allCountriesState.value =
                        ExploreUiState.Error(messageResId = R.string.ui_state_generic_error)
                }
            ) {
                val countries = api.getAllCountries()

                val filteredCountries = countries
                    .filter { it.independent == true }
                    .sortedBy { it.name.common }

                _allCountriesState.value = ExploreUiState.Success(
                    countries = filteredCountries,
                    totalCountries = filteredCountries.size
                )
            }
        }
    }

    fun fetchCountriesStats() {
        viewModelScope.launch {
            _statsState.value = StatsUiState.Loading

            safeApiCall(
                onHttpError = { code ->
                    _statsState.value = StatsUiState.Error(
                        messageResId = R.string.ui_state_http_exception,
                        code = code
                    )
                },
                onIoError = {
                    _statsState.value =
                        StatsUiState.Error(
                            messageResId = R.string.ui_state_io_exception)
                },
                onGenericError = {
                    _statsState.value =
                        StatsUiState.Error(messageResId = R.string.ui_state_generic_error)
                }
            ) {
                val countries = api.getAllCountries()
                val filteredCountries = countries.filter { it.independent == true }

                val stats = AllStats(
                    totalCountries = filteredCountries.size,
                    totalArea = filteredCountries.sumOf { it.area ?: 0.0 },
                    totalPopulation = filteredCountries.sumOf { it.population },
                    largest = filteredCountries.maxByOrNull { it.area ?: 0.0 },
                    mostPopulous = filteredCountries.maxByOrNull { it.population }
                )

                _statsState.value = StatsUiState.Success(
                    stats = stats
                )
            }
        }
    }

    fun fetchContinentStats() {
        viewModelScope.launch {
            safeApiCall(
                onHttpError = { code ->
                    _continentState.value = ContinentUiState.Error(
                        messageResId = R.string.ui_state_http_exception,
                        code = code
                    )
                },
                onIoError = {
                    _continentState.value =
                        ContinentUiState.Error(
                            messageResId = R.string.ui_state_io_exception)
                },
                onGenericError = {
                    _continentState.value =
                        ContinentUiState.Error(messageResId = R.string.ui_state_generic_error)
                }
            ) {
                val countries = api.getAllCountries()

                val stats = countries
                    .filter { it.independent == true }
                    .groupBy { it.region }
                    .map { (region, countries) ->
                        ContinentStats(
                            continent = region,
                            totalCountries = countries.size
                        )
                    }
                    .sortedBy { it.continent }

                _continentState.value = ContinentUiState.Success(continents = stats)
            }
        }
    }

    fun fetchCountriesByContinent(region: String) {
        viewModelScope.launch {
            _continentCountriesState.value = ExploreUiState.Loading

            safeApiCall(
                onHttpError = { code ->
                    _continentCountriesState.value = ExploreUiState.Error(
                        messageResId = R.string.ui_state_http_exception,
                        code = code
                    )
                },
                onIoError = {
                    _continentCountriesState.value =
                        ExploreUiState.Error(
                            messageResId = R.string.ui_state_io_exception)
                },
                onGenericError = {
                    _continentCountriesState.value =
                        ExploreUiState.Error(messageResId = R.string.ui_state_generic_error)
                }
            ) {
                val countries = api.getAllCountries()

                val filteredCountries = countries
                    .filter { it.independent == true && it.region == region }
                    .sortedBy { it.name.common }

                _continentCountriesState.value =
                    ExploreUiState.Success(
                        countries = filteredCountries,
                        totalCountries = filteredCountries.size
                    )
            }
        }
    }


    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun clearSearch() {
        _searchQuery.value = ""
    }

    fun allRetry() {
        fetchAllCountries()
        fetchTopLargestCountries()
        fetchCountriesStats()
        fetchTopPopulatedCountries()
        fetchContinentStats()
    }
}