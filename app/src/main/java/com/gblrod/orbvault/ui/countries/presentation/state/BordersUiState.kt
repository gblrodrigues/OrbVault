package com.gblrod.orbvault.ui.countries.presentation.state

import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto

sealed class BordersUiState {
    object Idle : BordersUiState()

    object Loading : BordersUiState()

    data class Success(
        val neighbors: List<CountriesDto>
    ) : BordersUiState()

    data class Error(
        val messageResId: Int,
        val code: Int? = null
    ) : BordersUiState()
}