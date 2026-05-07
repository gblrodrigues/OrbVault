package com.gblrod.orbvault.ui.countries.presentation.explore.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.data.countries.local.room.model.FavoriteCountry
import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.shared.components.CountryCard
import com.gblrod.orbvault.ui.shared.components.InfoRow
import com.gblrod.orbvault.ui.shared.components.ScreenHeader

@Composable
fun TopCountriesItems(
    countries: List<CountriesDto>,
    favorites: List<FavoriteCountry>,
    countryDetailsViewModel: CountryDetailsViewModel,
    primaryValue: String,
    secondValue: String,
    thirdValue: String,
    fourthValue: (CountriesDto) -> String,
    colorCustom: Color,
    onClick: (CountriesDto) -> Unit,
    labelWidth: Dp = 120.dp
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            ScreenHeader(
                primaryValue = primaryValue,
                secondValue = secondValue,
                colorCustom = colorCustom,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        itemsIndexed(countries) { index, country ->
            val isFavorite = favorites.any { it.code == country.cca3 }

            CountryCard(
                flag = country.flags.png,
                nameCommon = country.name.common,
                nameOfficial = country.name.official,
                isFavorite = isFavorite,
                index = index + 1,
                onFavoriteClick = { countryDetailsViewModel.toggleFavorite(country) },
                infoContent = {
                    InfoRow(
                        label = thirdValue,
                        value = fourthValue(country),
                        labelWidth = labelWidth
                    )
                },
                onClick = {
                    countryDetailsViewModel.onCountrySelected(code = country.cca3)
                    onClick(country)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }
    }
}