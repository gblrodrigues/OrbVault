package com.gblrod.orbvault.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.gblrod.orbvault.navigation.Routes
import com.gblrod.orbvault.ui.countries.presentation.explore.comparison.screen.ComparisonScreen
import com.gblrod.orbvault.ui.countries.presentation.explore.comparison.viewmodel.ComparisonViewModel
import com.gblrod.orbvault.ui.countries.presentation.explore.viewmodel.ExploreViewModel

fun NavGraphBuilder.comparisonRoute(
    exploreViewModel: ExploreViewModel,
    comparisonViewModel: ComparisonViewModel
) {
    composable(route = Routes.Comparison.route) {
        ComparisonScreen(
            exploreViewModel = exploreViewModel,
            comparisonViewModel = comparisonViewModel
        )
    }
}