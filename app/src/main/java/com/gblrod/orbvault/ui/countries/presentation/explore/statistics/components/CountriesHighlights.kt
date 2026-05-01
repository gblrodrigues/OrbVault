package com.gblrod.orbvault.ui.countries.presentation.explore.statistics.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.countries.presentation.explore.components.CountryBottomSheet
import com.gblrod.orbvault.ui.countries.presentation.explore.statistics.model.StatIcon
import com.gblrod.orbvault.ui.countries.presentation.explore.statistics.model.StatItem
import com.gblrod.orbvault.ui.countries.presentation.explore.statistics.util.formatCompactNumber
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.countries.presentation.state.ExploreUiState

@Composable
fun CountriesHighlights(
    exploreViewModel: ExploreViewModel,
    countryDetailsViewModel: CountryDetailsViewModel
) {
    val state by exploreViewModel.exploreUiState.collectAsState()
    val success = state as? ExploreUiState.GlobalStatsSucess ?: return

    val mostPopulous = success.stats.mostPopulous
    val largest = success.stats.largest

    var showSheet by remember { mutableStateOf(false) }
    var selectedCode by remember { mutableStateOf<String?>(null) }

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
                    countryDetailsViewModel.fetchCountryByCode(code = code)
                    selectedCode = code
                    showSheet = true
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
                    countryDetailsViewModel.fetchCountryByCode(code = code)
                    selectedCode = code
                    showSheet = true
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

    if (showSheet && selectedCode != null) {
        CountryBottomSheet(
            countryDetailsViewModel = countryDetailsViewModel,
            showBottomSheet = true,
            onDismiss = { selectedCode = null }
        )
    }
}