package com.gblrod.orbvault.ui.countries.presentation.explore.comparison.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gblrod.orbvault.navigation.Routes
import com.gblrod.orbvault.ui.countries.presentation.explore.comparison.screen.ComparisonScreen
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel

fun NavGraphBuilder.comparisonRoute(
    exploreViewModel: ExploreViewModel
) {
    composable(route = Routes.Comparison.route) {
        ComparisonScreen(exploreViewModel = exploreViewModel)
    }
}