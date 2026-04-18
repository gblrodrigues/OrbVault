package com.gblrod.orbvault.ui.presentation.state

import com.gblrod.orbvault.data.dto.CountriesDto

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