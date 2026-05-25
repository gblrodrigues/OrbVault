package com.gblrod.orbvault.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.gblrod.orbvault.navigation.Routes
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.home.components.SearchScreen
import com.gblrod.orbvault.ui.countries.presentation.home.viewmodel.CountriesViewModel

fun NavGraphBuilder.searchRoute(
    navHostController: NavHostController,
    countryDetailsViewModel: CountryDetailsViewModel,
    countriesViewModel: CountriesViewModel
) {
    composable(route = Routes.Search.route) {
        SearchScreen(
            countryDetailsViewModel = countryDetailsViewModel,
            countriesViewModel = countriesViewModel,
            onBack = { navHostController.popBackStack() }
        )
    }
}