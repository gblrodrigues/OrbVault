package com.gblrod.orbvault.ui.countries.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.countries.presentation.state.BordersUiState

@Composable
fun BorderCountriesRow(
    bordersState: BordersUiState,
    onCountryClick: (String) -> Unit,
    countryQuery: (String) -> Unit
) {
    when (bordersState) {

        is BordersUiState.Idle -> {}

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
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.neighbors_error),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}