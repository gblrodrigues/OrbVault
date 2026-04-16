package com.gblrod.orbvault.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gblrod.orbvault.ui.presentation.screen.ExploreScreen
import com.gblrod.orbvault.ui.presentation.screen.FavoritesScreen
import com.gblrod.orbvault.ui.presentation.screen.HomeScreen
import com.gblrod.orbvault.ui.presentation.viewmodel.CountriesViewModel

@Composable
fun NavigationGraph(
    paddingValues: PaddingValues,
    navHostController: NavHostController,
    countriesViewModel: CountriesViewModel
) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.Home.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ) {
        composable(route = Routes.Home.route) {
            HomeScreen(countriesViewModel = countriesViewModel)
        }

        composable(route = Routes.Explore.route) {
            ExploreScreen()
        }

        composable(route = Routes.Favorites.route) {
            FavoritesScreen()
        }
    }
}