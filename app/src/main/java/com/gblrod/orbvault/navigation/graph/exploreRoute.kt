package com.gblrod.orbvault.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.gblrod.orbvault.navigation.Routes
import com.gblrod.orbvault.ui.countries.presentation.explore.screen.ExploreScreen
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel

fun NavGraphBuilder.exploreRoute(
    navHostController: NavHostController,
    countryDetailsViewModel: CountryDetailsViewModel,
    exploreViewModel: ExploreViewModel
) {
    composable(route = Routes.Explore.route) {
        ExploreScreen(
            navHostController = navHostController,
            exploreViewModel = exploreViewModel,
            countryDetailsViewModel = countryDetailsViewModel
        )
    }
}