package com.gblrod.orbvault.ui.countries.presentation.explore.statistics.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.countries.presentation.state.StatsUiState

@Composable
fun StatsUiState.Error.getErrorMessage(
    showDetailedError: Boolean
): String {
    return when {
        !showDetailedError -> {
            stringResource(id = R.string.stats_ui_state_generic_error)
        }

        code != null -> {
            stringResource(id = messageResId, code)
        }

        else -> {
            stringResource(id = messageResId)
        }
    }
}