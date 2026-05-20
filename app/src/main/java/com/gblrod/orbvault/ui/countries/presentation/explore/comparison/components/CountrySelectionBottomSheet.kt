package com.gblrod.orbvault.ui.countries.presentation.explore.comparison.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto

@Composable
fun CountrySelectionBottomSheet(
    countries: List<CountriesDto>,
    onSelectedCountry: (CountriesDto) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = countries,
            key = { country -> country.cca3 }
        ) { country ->
            CountrySelectionItem(
                country = country,
                onClick = { onSelectedCountry(country) }
            )
        }
    }
}