package com.gblrod.orbvault.ui.presentation.explore.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.gblrod.orbvault.components.ErrorMessage
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.presentation.state.ExploreUiState

@Composable
fun PopulatedCountries(
    exploreViewModel: ExploreViewModel
) {
    val uiState by exploreViewModel.exploreUiState.collectAsState()

    when (val state = uiState) {

        is ExploreUiState.Loading -> {}

        is ExploreUiState.Success -> {
            PopulatedCountriesList(exploreViewModel = exploreViewModel)
        }

        is ExploreUiState.Error -> {
            val message = if (state.code == null) {
                stringResource(id = state.messageResId)
            } else {
                stringResource(id = state.messageResId, state.code)
            }

            ErrorMessage(
                message = message,
                onRetry = { exploreViewModel.fetchTopCountries() }
            )
        }
    }
}