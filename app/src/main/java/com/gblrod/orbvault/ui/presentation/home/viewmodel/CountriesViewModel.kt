package com.gblrod.orbvault.ui.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.network.CountriesAPI
import com.gblrod.orbvault.ui.presentation.state.CountriesUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class CountriesViewModel(
    val api: CountriesAPI
) : ViewModel() {
    private var job: Job? = null
    private val _countriesUiState = MutableStateFlow<CountriesUiState>(CountriesUiState.Loading)
    val countriesUiState: StateFlow<CountriesUiState> = _countriesUiState

    fun fetchCountry(country: String?) {
        job?.cancel()

        job = viewModelScope.launch {
            val firstLoad = _countriesUiState.value is CountriesUiState.Loading

            if (firstLoad) {
                _countriesUiState.value = CountriesUiState.Loading
            }

            try {
                val countriesResponse = api.findCountry(name = country)
                val countryResult = countriesResponse.firstOrNull()

                if (countryResult != null) {
                    _countriesUiState.value = CountriesUiState.Success(countryResult)
                } else {
                    _countriesUiState.value =
                        CountriesUiState.Error(R.string.countries_ui_state_not_found)
                }

            } catch (e: IOException) {
                _countriesUiState.value =
                    CountriesUiState.Error(messageResId = R.string.countries_ui_state_ioexception)

            } catch (e: HttpException) {
                if (e.code() == 404) {
                    _countriesUiState.value = CountriesUiState.Error(messageResId = R.string.countries_ui_state_not_found)

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
}