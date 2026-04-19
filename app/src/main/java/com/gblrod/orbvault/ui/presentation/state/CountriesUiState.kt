package com.gblrod.orbvault.ui.presentation.state

import com.gblrod.orbvault.data.dto.CountriesDto

sealed class CountriesUiState {
    object Idle : CountriesUiState()

    object Loading : CountriesUiState()

    data class Success(
        val country: CountriesDto,
        val code: CountriesDto,
        val borders: List<CountriesDto> = emptyList()
    ) : CountriesUiState()

    data class Error(
        val messageResId: Int,
        val code: Int? = null
    ) : CountriesUiState()
}