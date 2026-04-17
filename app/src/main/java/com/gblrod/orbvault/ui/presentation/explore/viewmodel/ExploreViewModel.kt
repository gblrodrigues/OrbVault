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

    private val _uiState = MutableStateFlow<ExploreUiState>(value = ExploreUiState.Loading)
    val exploreUiState: StateFlow<ExploreUiState> = _uiState

    fun fetchTopCountries() {
        viewModelScope.launch {
            _uiState.value = ExploreUiState.Loading

            try {
                val countries = api.getAllCountries()

                val top10PopulatedCountries = countries
                    .sortedByDescending { it.population }
                    .take(n = 10)

                _uiState.value = ExploreUiState.Success(countries =  top10PopulatedCountries)

            } catch (e: HttpException) {
                _uiState.value = ExploreUiState.Error(
                    messageResId = R.string.explore_ui_state_httpexception,
                    code = e.code()
                )

            } catch (e: IOException) {
                _uiState.value = ExploreUiState.Error(messageResId = R.string.explore_ui_state_ioexception)
            }
        }
    }
}