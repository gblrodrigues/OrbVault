package com.gblrod.orbvault.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gblrod.orbvault.navigation.Routes
import com.gblrod.orbvault.ui.countries.presentation.explore.populated.screen.PopulatedCountriesScreen
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel

fun NavGraphBuilder.populatedCountriesRoute(
    countryDetailsViewModel: CountryDetailsViewModel,
    exploreViewModel: ExploreViewModel
) {
    composable(route = Routes.PopulatedCountries.route) {
        PopulatedCountriesScreen(
            exploreViewModel = exploreViewModel,
            countryDetailsViewModel = countryDetailsViewModel
        )
    }
}