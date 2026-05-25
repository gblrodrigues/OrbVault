package com.gblrod.orbvault.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gblrod.orbvault.navigation.Routes
import com.gblrod.orbvault.ui.countries.presentation.explore.all.screen.AllCountriesScreen
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel

fun NavGraphBuilder.allCountriesRoute(
    countryDetailsViewModel: CountryDetailsViewModel,
    exploreViewModel: ExploreViewModel
) {
    composable(route = Routes.AllCountries.route) {
        AllCountriesScreen(
            exploreViewModel = exploreViewModel,
            countryDetailsViewModel = countryDetailsViewModel
        )
    }
}