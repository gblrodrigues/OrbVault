package com.gblrod.orbvault.components

import com.gblrod.orbvault.dto.CountriesDto

sealed class CountriesUiState {
    object Loading : CountriesUiState()
    data class Success(
        val country: CountriesDto
    ) : CountriesUiState()

    data class Error(
        val messageResId: Int,
        val code: Int? = null
    ) : CountriesUiState()
}