package com.gblrod.orbvault.ui.presentation.explore.populated.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.ui.presentation.explore.components.ExploreItems
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.theme.PopulatedCountriesColor

@Composable
fun PopulatedCountriesList(
    exploreViewModel: ExploreViewModel,
    countryDetailsViewModel: CountryDetailsViewModel
) {
    ExploreItems(
        exploreViewModel = exploreViewModel,
        countryDetailsViewModel = countryDetailsViewModel,
        primaryValue = stringResource(id = R.string.populated_country_label_explore),
        secondValue = stringResource(id = R.string.populated_country_label_explore_top10),
        colorCustom = PopulatedCountriesColor,
        thirdValue = stringResource(id = R.string.populated_country_label_population),
        fourthValue = { "%,d".format(it.population) },
        labelWidth = 100.dp,
        onClick = { country ->
            countryDetailsViewModel.fetchCountryByCode(code = country.cca3)
        }
    )
}