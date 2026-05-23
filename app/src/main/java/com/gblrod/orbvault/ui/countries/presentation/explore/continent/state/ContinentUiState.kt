package com.gblrod.orbvault.ui.countries.presentation.explore.continent.state

import com.gblrod.orbvault.ui.countries.presentation.explore.continent.model.ContinentStats

sealed class ContinentUiState {
    object Loading : ContinentUiState()

    data class Success(
        val continents: List<ContinentStats>
    ) : ContinentUiState()

    data class Error(
        val messageResId: Int,
        val code: Int? = null
    ) : ContinentUiState()
}