package com.gblrod.orbvault.ui.presentation.state

import com.gblrod.orbvault.data.dto.CountriesDto

sealed class ExploreUiState {
    object Loading : ExploreUiState()

    data class Success(
        val countries: List<CountriesDto>
    ) : ExploreUiState()

    data class Error(
        val messageResId: Int,
        val code: Int? = null
    ) : ExploreUiState()
}