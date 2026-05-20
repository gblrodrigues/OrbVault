package com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.countries.remote.api.CountriesAPI
import com.gblrod.orbvault.ui.countries.presentation.explore.statistics.model.AllStats
import com.gblrod.orbvault.ui.countries.presentation.state.ExploreUiState
import com.gblrod.orbvault.ui.countries.presentation.state.StatsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

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

    init {
        if (_statsState.value !is StatsUiState.Success) {
            fetchCountriesStats()
        }
    }

    fun fetchTopPopulatedCountries() {
        viewModelScope.launch {
            _topPopulatedState.value = ExploreUiState.Loading

            try {
                val countries = api.getAllCountries()

                val top10PopulatedCountries = countries
                    .sortedByDescending { it.population }
                    .take(n = 10)

                _topPopulatedState.value = ExploreUiState.Success(
                    countries = top10PopulatedCountries,
                    totalCountries = top10PopulatedCountries.size
                )

            } catch (e: HttpException) {
                _topPopulatedState.value = ExploreUiState.Error(
                    messageResId = R.string.ui_state_http_exception,
                    code = e.code()
                )

            } catch (e: IOException) {
                _topPopulatedState.value =
                    ExploreUiState.Error(messageResId = R.string.ui_state_io_exception)

            } catch (e: Exception) {
                if (e is CancellationException) throw e
                _topPopulatedState.value =
                    ExploreUiState.Error(messageResId = R.string.ui_state_generic_error)
            }
        }
    }

    fun fetchTopLargestCountries() {
        viewModelScope.launch {
            _largestCountriesState.value = ExploreUiState.Loading

            try {
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

            } catch (e: HttpException) {
                _largestCountriesState.value = ExploreUiState.Error(
                    messageResId = R.string.ui_state_http_exception,
                    code = e.code()
                )

            } catch (e: IOException) {
                _largestCountriesState.value =
                    ExploreUiState.Error(messageResId = R.string.ui_state_io_exception)

            } catch (e: Exception) {
                if (e is CancellationException) throw e
                _largestCountriesState.value =
                    ExploreUiState.Error(messageResId = R.string.ui_state_generic_error)
            }
        }
    }

    fun fetchAllCountries() {
        viewModelScope.launch {
            _allCountriesState.value = ExploreUiState.Loading

            try {
                val countries = api.getAllCountries()

                val filteredCountries = countries
                    .filter { it.independent == true }
                    .sortedBy { it.name.common }

                _allCountriesState.value = ExploreUiState.Success(
                    countries = filteredCountries,
                    totalCountries = filteredCountries.size
                )

            } catch (e: HttpException) {
                _allCountriesState.value = ExploreUiState.Error(
                    messageResId = R.string.ui_state_http_exception,
                    code = e.code()
                )

            } catch (e: IOException) {
                _allCountriesState.value =
                    ExploreUiState.Error(messageResId = R.string.ui_state_io_exception)

            } catch (e: Exception) {
                if (e is CancellationException) throw e
                _allCountriesState.value =
                    ExploreUiState.Error(messageResId = R.string.ui_state_generic_error)
            }
        }
    }

    fun fetchCountriesStats() {
        viewModelScope.launch {
            _statsState.value = StatsUiState.Loading

            try {
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

            } catch (e: HttpException) {
                _statsState.value = StatsUiState.Error(
                    messageResId = R.string.ui_state_http_exception,
                    code = e.code()
                )

            } catch (e: IOException) {
                _statsState.value =
                    StatsUiState.Error(messageResId = R.string.ui_state_io_exception)

            } catch (e: Exception) {
                if (e is CancellationException) throw e
                _statsState.value =
                    StatsUiState.Error(messageResId = R.string.ui_state_generic_error)
            }
        }
    }

    fun allRetry() {
        fetchAllCountries()
        fetchTopLargestCountries()
        fetchCountriesStats()
        fetchTopPopulatedCountries()
    }
}