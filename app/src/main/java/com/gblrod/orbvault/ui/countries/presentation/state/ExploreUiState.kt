package com.gblrod.orbvault.ui.countries.presentation.state

import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto
import com.gblrod.orbvault.ui.countries.presentation.explore.statistics.model.AllStats

sealed class ExploreUiState {
    object Loading : ExploreUiState()

    data class Success(
        val countries: List<CountriesDto>
    ) : ExploreUiState()

    data class GlobalStatsSucess(
        val stats: AllStats,
    ) : ExploreUiState()

    data class Error(
        val messageResId: Int,
        val code: Int? = null
    ) : ExploreUiState()
}