package com.gblrod.orbvault.ui.countries.presentation.explore.continent.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gblrod.orbvault.navigation.Routes
import com.gblrod.orbvault.ui.countries.presentation.explore.continent.screen.ContinentScreen
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.CountryDetailsViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel

fun NavGraphBuilder.continentRoute(
    exploreViewModel: ExploreViewModel,
    countryDetailsViewModel: CountryDetailsViewModel
) {
    composable(route = Routes.Continent.route
    ) { backStackEntry ->
        val region = backStackEntry.arguments?.getString("region") ?: return@composable

        ContinentScreen(
            region = region,
            exploreViewModel = exploreViewModel,
            countryDetailsViewModel = countryDetailsViewModel
        )
    }
}