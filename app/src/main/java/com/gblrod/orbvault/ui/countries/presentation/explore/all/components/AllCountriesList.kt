package com.gblrod.orbvault.ui.countries.presentation.explore.all.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.theme.PopulatedCountries

@Composable
fun AllCountriesList(
    exploreViewModel: ExploreViewModel,
    countryDetailsViewModel: CountryDetailsViewModel,
    countriesSize: Int
) {
    AllCountriesItems(
        exploreViewModel = exploreViewModel,
        countryDetailsViewModel = countryDetailsViewModel,
        primaryValue = stringResource(id = R.string.common_label_explore),
        secondValue = stringResource(
            id = R.string.all_countries_label,
            countriesSize),
        colorCustom = PopulatedCountries,
        onClick = { country ->
            countryDetailsViewModel.fetchCountryByCode(code = country.cca3)
        }
    )
}