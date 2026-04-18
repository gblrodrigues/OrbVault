package com.gblrod.orbvault.ui.presentation.explore.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.network.CountriesAPI
import com.gblrod.orbvault.ui.presentation.state.ExploreUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class ExploreViewModel(
    private val api: CountriesAPI
) : ViewModel() {

    private val _exploreUiState = MutableStateFlow<ExploreUiState>(value = ExploreUiState.Loading)
    val exploreUiState: StateFlow<ExploreUiState> = _exploreUiState

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
                    messageResId = R.string.explore_ui_state_httpexception,
                    code = e.code()
                )

            } catch (e: IOException) {
                _exploreUiState.value =
                    ExploreUiState.Error(messageResId = R.string.explore_ui_state_ioexception)
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
                    messageResId = R.string.explore_ui_state_httpexception,
                    code = e.code()
                )

            } catch (e: IOException) {
                _exploreUiState.value =
                    ExploreUiState.Error(messageResId = R.string.explore_ui_state_ioexception)
            }
        }
    }
}