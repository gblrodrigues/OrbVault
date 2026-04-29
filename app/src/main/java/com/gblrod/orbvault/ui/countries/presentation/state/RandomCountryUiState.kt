package com.gblrod.orbvault.ui.countries.presentation.state

import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto

sealed class RandomCountryUiState {
    object Idle : RandomCountryUiState()

    object Loading : RandomCountryUiState()

    data class Success(
        val country: CountriesDto
    ) : RandomCountryUiState()

    data class Error(
        val messageResId: Int,
        val code: Int? = null
    ) : RandomCountryUiState()
}