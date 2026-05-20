package com.gblrod.orbvault.ui.countries.presentation.explore.comparison.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto
import com.gblrod.orbvault.ui.countries.presentation.explore.comparison.utils.getWinner
import com.gblrod.orbvault.ui.countries.presentation.explore.statistics.util.formatCompactNumber

@Composable
fun ComparisonResult(
    primaryCountry: CountriesDto,
    secondaryCountry: CountriesDto
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ComparisonHeader(primaryCountry)

            Text(
                text = stringResource(id = R.string.comparison_vs),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )

            ComparisonHeader(secondaryCountry)
        }
        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(16.dp))

        ComparisonInfoRow(
            label = stringResource(id = R.string.country_details_label_capital),
            primaryValue = primaryCountry.capital?.firstOrNull() ?: "N/A",
            secondaryValue = secondaryCountry.capital?.firstOrNull() ?: "N/A"
        )

        ComparisonInfoRow(
            label = stringResource(id = R.string.country_details_label_population),
            primaryValue = formatCompactNumber(primaryCountry.population.toDouble()),
            secondaryValue = formatCompactNumber(secondaryCountry.population.toDouble()),
            winner = getWinner(
                primaryValue = primaryCountry.population.toDouble(),
                secondaryValue = secondaryCountry.population.toDouble()
            )
        )

        ComparisonInfoRow(
            label = stringResource(id = R.string.country_details_label_area),
            primaryValue = formatCompactNumber(primaryCountry.area ?: 0.0),
            secondaryValue = formatCompactNumber(secondaryCountry.area ?: 0.0),
            winner = getWinner(
                primaryValue = primaryCountry.area ?: 0.0,
                secondaryValue = secondaryCountry.area ?: 0.0
            )
        )

        ComparisonInfoRow(
            label = stringResource(id = R.string.neighbors_more_label),
            primaryValue = "${primaryCountry.borders?.size ?: 0}",
            secondaryValue = "${secondaryCountry.borders?.size ?: 0}",
            winner = getWinner(
                primaryValue = primaryCountry.borders?.size?.toDouble() ?: 0.0,
                secondaryValue = secondaryCountry.borders?.size?.toDouble() ?: 0.0
            )
        )

        ComparisonInfoRow(
            label = stringResource(id = R.string.country_details_label_currencies),
            primaryValue = primaryCountry.currencies?.values?.firstOrNull()?.name ?: "N/A",
            secondaryValue = secondaryCountry.currencies?.values?.firstOrNull()?.name ?: "N/A"
        )

        ComparisonInfoRow(
            label = stringResource(id = R.string.country_details_label_region),
            primaryValue = primaryCountry.region,
            secondaryValue = secondaryCountry.region
        )
    }
}