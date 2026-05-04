package com.gblrod.orbvault.ui.countries.presentation.explore.statistics.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.countries.presentation.explore.statistics.model.StatIcon
import com.gblrod.orbvault.ui.countries.presentation.explore.statistics.model.StatItem
import com.gblrod.orbvault.ui.countries.presentation.explore.statistics.util.formatCompactNumber
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.countries.presentation.state.StatsUiState

@Composable
fun CountriesHighlights(
    exploreViewModel: ExploreViewModel,
    onCountryClick: (String) -> Unit,
) {
    val state by exploreViewModel.statsState.collectAsState()
    val success = state as? StatsUiState.Success ?: return

    val mostPopulous = success.stats.mostPopulous
    val largest = success.stats.largest

    val statsItems = listOf(
        StatItem(
            label = stringResource(
                id = R.string.statistics_most_populous_label,
                mostPopulous?.name?.common ?: "N/A"
            ),
            value = formatCompactNumber(value = mostPopulous?.population?.toDouble() ?: 0.0),
            icon = mostPopulous?.flags?.png?.let {
                StatIcon.Remote(url = it)
            } ?: StatIcon.Local(resId = R.drawable.logo),
            onClick = {
                val code = mostPopulous?.cca3

                if (code != null) {
                    onCountryClick(code)
                }
            }
        ),
        StatItem(
            label = stringResource(
                id = R.string.statistics_most_largest_label,
                largest?.name?.common ?: "N/A"
            ),
            value = formatCompactNumber(value = largest?.area ?: 0.0),
            icon = largest?.flags?.png?.let {
                StatIcon.Remote(url = it)
            } ?: StatIcon.Local(resId = R.drawable.logo),
            onClick = {
                val code = largest?.cca3

                if (code != null) {
                    onCountryClick(code)
                }
            }
        )
    )
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        statsItems.chunked(size = 2).forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { item ->
                    StatisticsItem(
                        modifier = Modifier.weight(1f),
                        image = item.icon,
                        totalValue = item.value,
                        descValue = item.label,
                        onClick = item.onClick
                    )
                }

                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}