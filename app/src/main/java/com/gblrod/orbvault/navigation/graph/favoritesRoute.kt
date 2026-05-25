package com.gblrod.orbvault.navigation.graph

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.gblrod.orbvault.navigation.Routes
import com.gblrod.orbvault.navigation.extensions.navigateToBottomBar
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.favorites.screen.FavoritesScreen

fun NavGraphBuilder.favoritesRoute(
    navHostController: NavHostController,
    countryDetailsViewModel: CountryDetailsViewModel,
    snackbarHostState: SnackbarHostState
) {
    composable(route = Routes.Favorites.route) {
        FavoritesScreen(
            countryDetailsViewModel = countryDetailsViewModel,
            snackbarHostState = snackbarHostState,
            onNavigateExplore = {
                navHostController.navigateToBottomBar(route = Routes.Explore.route)
            }
        )
    }
}