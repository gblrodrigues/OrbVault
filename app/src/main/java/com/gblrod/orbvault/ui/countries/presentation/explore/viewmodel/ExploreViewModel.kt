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
import java.io.IOException
import retrofit2.HttpException
import kotlin.coroutines.cancellation.CancellationException

class ExploreViewModel(
    private val api: CountriesAPI
) : ViewModel() {

    private val _exploreUiState = MutableStateFlow<ExploreUiState>(value = ExploreUiState.Loading)
    val exploreUiState: StateFlow<ExploreUiState> = _exploreUiState

    private val _statsState = MutableStateFlow<StatsUiState>(value = StatsUiState.Loading)
    val statsState: StateFlow<StatsUiState> = _statsState

    init {
        if (_statsState.value !is StatsUiState.Success) {
            fetchAllCountries()
        }
    }

    fun fetchTopPopulatedCountries() {
        viewModelScope.launch {
            _exploreUiState.value = ExploreUiState.Loading

            try {
                val countries = api.getAllCountries()

                val top10PopulatedCountries = countries
                    .sortedByDescending { it.population }
                    .take(n = 10)

                _exploreUiState.value = ExploreUiState.Success(countries = top10PopulatedCountries)

            } catch (e: HttpException) {
                _exploreUiState.value = ExploreUiState.Error(
                    messageResId = R.string.ui_state_http_exception,
                    code = e.code()
                )

            } catch (e: IOException) {
                _exploreUiState.value =
                    ExploreUiState.Error(messageResId = R.string.ui_state_io_exception)

            } catch (e: Exception) {
                if (e is CancellationException) throw e
                _exploreUiState.value =
                    ExploreUiState.Error(messageResId = R.string.ui_state_generic_error)
            }
        }
    }

    fun fetchTopLargestCountries() {
        viewModelScope.launch {
            _exploreUiState.value = ExploreUiState.Loading

            try {
                val countries = api.getAllCountries()

                val top10LargestCountries = countries
                    .filter { it.independent == true }
                    .sortedByDescending { it.area }
                    .take(n = 10)

                _exploreUiState.value = ExploreUiState.Success(countries = top10LargestCountries)

            } catch (e: HttpException) {
                _exploreUiState.value = ExploreUiState.Error(
                    messageResId = R.string.ui_state_http_exception,
                    code = e.code()
                )

            } catch (e: IOException) {
                _exploreUiState.value =
                    ExploreUiState.Error(messageResId = R.string.ui_state_io_exception)

            } catch (e: Exception) {
                if (e is CancellationException) throw e
                _exploreUiState.value =
                    ExploreUiState.Error(messageResId = R.string.ui_state_generic_error)
            }
        }
    }

    fun fetchAllCountries() {
        viewModelScope.launch {
            _statsState.value = StatsUiState.Loading

            try {
                val countries = api.getAllCountries()
                val filteredCountries = countries.filter { it.independent == true }

                val stats = AllStats(
                    totalCountries = filteredCountries.size,
                    totalArea = filteredCountries.sumOf { it.area ?: 0.0 },
                    totalPopulation = filteredCountries.sumOf { it.population},
                    largest = filteredCountries.maxByOrNull { it.area ?: 0.0 },
                    mostPopulous = filteredCountries.maxByOrNull { it.population}
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
}