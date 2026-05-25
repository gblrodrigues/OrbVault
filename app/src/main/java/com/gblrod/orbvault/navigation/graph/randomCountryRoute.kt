package com.gblrod.orbvault.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gblrod.orbvault.navigation.Routes
import com.gblrod.orbvault.ui.countries.presentation.explore.random.screen.RandomCountryScreen
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.home.viewmodel.CountriesViewModel

fun NavGraphBuilder.randomCountryRoute(
    countryDetailsViewModel: CountryDetailsViewModel,
    countriesViewModel: CountriesViewModel
) {
    composable(route = Routes.RandomCountry.route) {
        RandomCountryScreen(
            countriesViewModel = countriesViewModel,
            countryDetailsViewModel = countryDetailsViewModel
        )
    }
}