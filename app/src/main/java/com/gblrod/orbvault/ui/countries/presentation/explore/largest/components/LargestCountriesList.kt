package com.gblrod.orbvault.ui.countries.presentation.explore.largest.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.countries.local.room.model.FavoriteCountry
import com.gblrod.orbvault.data.countries.remote.dto.CountriesDto
import com.gblrod.orbvault.ui.countries.presentation.explore.components.TopCountriesItems
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.theme.LargestCountries

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LargestCountriesList(
    countryDetailsViewModel: CountryDetailsViewModel,
    countries: List<CountriesDto>,
    favorites: List<FavoriteCountry>
) {
    TopCountriesItems(
        countries = countries,
        favorites = favorites,
        countryDetailsViewModel = countryDetailsViewModel,
        primaryValue = stringResource(id = R.string.largest_country_label_explore),
        secondValue = stringResource(id = R.string.largest_country_label_explore_top10),
        colorCustom = LargestCountries,
        thirdValue = stringResource(id = R.string.largest_country_label_area),
        fourthValue = { "%,.2f".format(it.area) },
        labelWidth = 50.dp,
        onClick = { country ->
            countryDetailsViewModel.fetchCountryByCode(code = country.cca3)
        }
    )
}