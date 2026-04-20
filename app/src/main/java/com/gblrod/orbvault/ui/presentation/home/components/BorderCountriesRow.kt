package com.gblrod.orbvault.ui.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.presentation.state.BordersUiState

@Composable
fun BorderCountriesRow(
    bordersState: BordersUiState,
    onCountryClick: (String) -> Unit,
    countryQuery: (String) -> Unit
) {
    when (bordersState) {
        is BordersUiState.Loading -> {
            CircularProgressIndicator()
        }

        is BordersUiState.Success -> {
            val neighbors = bordersState.neighbors

            if (neighbors.isNotEmpty()) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(neighbors) { neighbor ->
                        NeighborCountryCard(
                            country = neighbor,
                            onCountryClick = onCountryClick,
                            countryQuery = countryQuery
                        )
                    }
                }
            }
        }

        is BordersUiState.Error -> {
            Text(
                text = stringResource(id = R.string.neighbors_error),
                color = Color.DarkGray
            )
        }

        else -> {}
    }
}