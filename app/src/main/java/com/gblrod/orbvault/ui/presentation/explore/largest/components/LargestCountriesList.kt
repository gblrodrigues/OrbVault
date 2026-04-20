package com.gblrod.orbvault.ui.presentation.explore.largest.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.gblrod.orbvault.R
import com.gblrod.orbvault.data.dto.CountriesDto
import com.gblrod.orbvault.ui.presentation.explore.components.ExploreItems
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.theme.LargestCountriesColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LargestCountriesList(
    exploreViewModel: ExploreViewModel,
    countryDetailsViewModel: CountryDetailsViewModel
) {
    ExploreItems(
        exploreViewModel = exploreViewModel,
        countryDetailsViewModel = countryDetailsViewModel,
        primaryValue = stringResource(id = R.string.largest_country_label_explore),
        secondValue = stringResource(id = R.string.largest_country_label_explore_top10),
        colorCustom = LargestCountriesColor,
        thirdValue = stringResource(id = R.string.largest_country_label_area),
        fourthValue = { "%,.2f".format(it.area) },
        labelWidth = 50.dp,
        onClick = { country ->
            countryDetailsViewModel.fetchCountry(country = country.name.common)
        }
    )
}