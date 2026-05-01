package com.gblrod.orbvault.ui.countries.presentation.explore.statistics.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.countries.presentation.explore.statistics.components.StatisticsItem
import com.gblrod.orbvault.ui.countries.presentation.explore.statistics.model.StatIcon
import com.gblrod.orbvault.ui.countries.presentation.explore.statistics.model.StatItem
import com.gblrod.orbvault.ui.countries.presentation.explore.statistics.util.formatCompactNumber
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.countries.presentation.state.StatsUiState

@Composable
fun MoreStatisticsScreen(
    exploreViewModel: ExploreViewModel,
    colorCustom: Color
) {
    val state by exploreViewModel.statsState.collectAsState()
    val success = state as? StatsUiState.Success ?: return

    val totalCountries = success.stats.totalCountries
    val totalPopulation = success.stats.totalPopulation
    val totalArea = success.stats.totalArea

    val primaryValue = stringResource(id = R.string.statistics_label_explore)
    val secondValue = stringResource(id = R.string.statistics_label_description)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(
                text = primaryValue,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = secondValue,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = colorCustom
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        val statsItems = listOf(
            StatItem(
                label = stringResource(id = R.string.statistics_country_label),
                value = formatCompactNumber(value = totalCountries.toDouble()),
                icon = StatIcon.Local(resId = R.drawable.logo)
            ),
            StatItem(
                label = stringResource(id = R.string.statistics_population_label),
                value = formatCompactNumber(value = totalPopulation.toDouble()),
                icon = StatIcon.Local(resId = R.drawable.logo)
            ),
            StatItem(
                label = stringResource(id = R.string.statistics_area_label),
                value = formatCompactNumber(value = totalArea),
                icon = StatIcon.Local(resId = R.drawable.logo)
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
}