package com.gblrod.orbvault.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gblrod.orbvault.ui.presentation.explore.largest.screen.LargestCountriesScreen
import com.gblrod.orbvault.ui.presentation.explore.populated.screen.PopulatedCountriesScreen
import com.gblrod.orbvault.ui.presentation.explore.random.screen.RandomCountryScreen
import com.gblrod.orbvault.ui.presentation.explore.screen.ExploreScreen
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.presentation.explore.viewmodel.ExploreViewModel
import com.gblrod.orbvault.ui.presentation.favorites.screen.FavoritesScreen
import com.gblrod.orbvault.ui.presentation.home.screen.HomeScreen
import com.gblrod.orbvault.ui.presentation.home.viewmodel.CountriesViewModel

@Composable
fun NavigationGraph(
    paddingValues: PaddingValues,
    navHostController: NavHostController,
    countriesViewModel: CountriesViewModel,
    exploreViewModel: ExploreViewModel,
    countryDetailsViewModel: CountryDetailsViewModel
) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.Home.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(route = Routes.Home.route) {
            HomeScreen(
                countriesViewModel = countriesViewModel,
                countryDetailsViewModel = countryDetailsViewModel
            )
        }

        composable(route = Routes.Explore.route) {
            ExploreScreen(
                navHostController = navHostController,
                exploreViewModel = exploreViewModel,
                countriesViewModel = countriesViewModel
            )
        }

        composable(route = Routes.Favorites.route) {
            FavoritesScreen(
                countryDetailsViewModel = countryDetailsViewModel
            )
        }

        composable(route = Routes.PopulatedCountries.route) {
            PopulatedCountriesScreen(
                exploreViewModel = exploreViewModel,
                countryDetailsViewModel = countryDetailsViewModel
            )
        }

        composable(route = Routes.LargestCountries.route) {
            LargestCountriesScreen(
                exploreViewModel = exploreViewModel,
                countryDetailsViewModel = countryDetailsViewModel
            )
        }

        composable(route = Routes.RandomCountry.route) {
            RandomCountryScreen(
                countriesViewModel = countriesViewModel,
                countryDetailsViewModel = countryDetailsViewModel
            )
        }
    }
}