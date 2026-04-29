package com.gblrod.orbvault.ui.countries.presentation.state

import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto

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