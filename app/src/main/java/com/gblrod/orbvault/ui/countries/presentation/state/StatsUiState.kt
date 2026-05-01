package com.gblrod.orbvault.ui.countries.presentation.state

import com.gblrod.orbvault.ui.countries.presentation.explore.statistics.model.AllStats

sealed class StatsUiState {
    object Loading : StatsUiState()

    data class Success(
        val stats: AllStats
    ) : StatsUiState()

    data class Error(
        val messageResId: Int,
        val code: Int? = null
    ) : StatsUiState()
}